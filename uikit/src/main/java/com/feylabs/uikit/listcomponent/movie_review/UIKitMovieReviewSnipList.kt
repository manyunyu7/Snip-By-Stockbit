package com.feylabs.uikit.listcomponent.movie_review

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.core.helper.view.ViewUtils.gone
import com.feylabs.core.helper.view.ViewUtils.visible
import com.feylabs.uikit.R
import com.feylabs.uikit.databinding.CustomUikitListSnipBinding
import com.feylabs.uikit.listcomponent.uikitmodel.GenerateDummyData
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.uikit.state.UIKitState
import com.feylabs.uikit.util.RecyclerViewUtil.setVerticalLayoutManager


class UIKitMovieReviewSnipList : ConstraintLayout {

    var pastVisiblesItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    private var calledId = mutableListOf(1)
    var currentPage = 1;

    lateinit var onClickInterface : OnSnipListClickInterface

    private val binding: CustomUikitListSnipBinding =
        CustomUikitListSnipBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    var loadMoreListener: LoadMoreListener? = null

    private var onActionClick: (() -> Unit) = {}

    private var uiStateFlow = (UIKitState.DEFAULT)


    private val mAdapter by lazy { UIKitMovieReviewItemAdapter() }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        if (isInEditMode.not()) {
            initRecyclerView()
            initAdapterClick()
            //loadDummyData()
        }
        showSkeleton()
    }

    fun setUiState(state: UIKitState = UIKitState.DEFAULT) {
        this.uiStateFlow = state
        updateUiBasedOnState()
    }

    private fun updateUiBasedOnState() {
        when (uiStateFlow) {
            UIKitState.LOADING -> {
                if (calledId.isEmpty().not()) {
                    //binding.progressBar.visible()
                } else {
                    showSkeleton()
                }
            }
            UIKitState.SUCCESS -> {
            }
            UIKitState.ERROR -> {
            }
            UIKitState.EMPTY -> {
                if (calledId.isEmpty()) {
                    hideSkeleton()
                }
            }
            UIKitState.DEFAULT -> showToast(context, "Default")
        }

        if (uiStateFlow != UIKitState.LOADING) {
            //binding.progressBar.gone()
            hideSkeleton()
        }
    }

    fun loadDummyData() {
    }

    private fun initAdapterClick() {
        mAdapter.setInterface(object : UIKitMovieReviewItemAdapter.ItemInterface {

            override fun onClick(data: MovieReviewUiKitModel) {
                if(::onClickInterface.isInitialized){
                    onClickInterface.onClick(data.id.toString())
                }
            }
        })
    }

    fun setClickInterface(mInterface : OnSnipListClickInterface){
        this.onClickInterface = mInterface
    }

    fun showSkeleton(count: Int = 3) {
        val parentLayout = binding.uikitListSnipSkeletonContainer
        val marginTop = resources.getDimensionPixelSize(R.dimen.dimen_10dp)
        for (i in 1..count) {
            val include = LayoutInflater.from(parentLayout.context)
                .inflate(R.layout.uikit_skeleton_snips_item, parentLayout, false)
            parentLayout.addView(include)
        }
        binding.uikitListSnipSkeletonContainer.visibility = View.VISIBLE
        binding.rvUikitListSnip.visibility = View.GONE
    }

    fun hideSkeleton() {
        val parentLayout = binding.uikitListSnipSkeletonContainer
        parentLayout.removeAllViews()
        binding.uikitListSnipSkeletonContainer.gone()
        binding.rvUikitListSnip.visible()
    }

    private fun initRecyclerView() {
        binding.rvUikitListSnip.apply {
            this.adapter = mAdapter
            setVerticalLayoutManager(context)
        }

        binding.rvUikitListSnip.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val mLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount()
                    totalItemCount = mLayoutManager.getItemCount()
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + pastVisiblesItems >= totalItemCount-2) {
                        currentPage++
                        // Do pagination.. i.e. fetch new data
                        loadMoreListener?.onLoadMore(
                            lastId = currentPage,
                            isCalled = calledId.contains(mAdapter.getLastId()),
                            calledId = calledId.toString()
                        )
                        calledId.add(currentPage)
                    }
                }
            }
        })
    }

    fun getItemCount() = mAdapter.itemCount

    fun addItem(item: MovieReviewUiKitModel) {
        val existingItemIndex = mAdapter.data.indexOfFirst { it.id == item.id }
        if (existingItemIndex != -1) {
            mAdapter.data[existingItemIndex] = item
            mAdapter.notifyItemChanged(existingItemIndex)
        } else {
            val position = mAdapter.itemCount
            mAdapter.data.add(item)
            mAdapter.notifyItemInserted(position)
        }
    }

    fun addDatas(datas: List<MovieReviewUiKitModel>) {
        val itemsToAdd = mutableListOf<MovieReviewUiKitModel>()
        for (item in datas) {
            val existingItemIndex = mAdapter.data.indexOfFirst { it.id == item.id }
            if (existingItemIndex != -1) {
                mAdapter.data[existingItemIndex] = item
                mAdapter.notifyItemChanged(existingItemIndex)
            } else {
                itemsToAdd.add(item)
            }
        }
        if (itemsToAdd.isNotEmpty()) {
            val positionStart = mAdapter.itemCount
            mAdapter.data.addAll(itemsToAdd)
            mAdapter.notifyItemRangeInserted(positionStart, itemsToAdd.size)
        }

        if (mAdapter.itemCount == 0) {
            showSkeleton(3)
        } else {
            hideSkeleton()
        }
    }

    fun onUnboxingItemClick(action: (() -> Unit)? = null) {
        if (action != null) {
            onActionClick = action
        }
    }

    fun clear() {
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()

        if (mAdapter.itemCount == 0) {
            showSkeleton(3)
        }
    }

    interface LoadMoreListener {
        fun onLoadMore(lastId: Int, calledId: String, isCalled: Boolean)
    }

    interface OnSnipListClickInterface{
        fun onClick(link:String)
    }

}