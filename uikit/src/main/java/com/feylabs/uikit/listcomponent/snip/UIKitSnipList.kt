package com.feylabs.uikit.listcomponent.snip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feylabs.uikit.R
import com.feylabs.uikit.databinding.CustomUikitListSnipBinding
import com.feylabs.uikit.listcomponent.uikitmodel.GenerateDummyData
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.uikit.util.RecyclerViewUtil.setVerticalLayoutManager

class UIKitSnipList : ConstraintLayout {

    private val binding: CustomUikitListSnipBinding =
        CustomUikitListSnipBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    var loadMoreListener: LoadMoreListener? = null

    private var onActionClick: (() -> Unit) = {}


    private val mAdapter by lazy { UIKitSnipItemAdapter() }

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

    fun loadDummyData() {
        addDatas(GenerateDummyData.createData())
    }

    private fun initAdapterClick() {
        mAdapter.setInterface(object : UIKitSnipItemAdapter.ItemInterface {
            override fun onClick(data: UnboxingSectoralUIKitModel) {
                onActionClick.invoke()
            }
        })
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
        binding.uikitListSnipSkeletonContainer.visibility = View.GONE
        binding.rvUikitListSnip.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        binding.rvUikitListSnip.apply {
            this.adapter = mAdapter
            setVerticalLayoutManager(context)
        }


        binding.rvUikitListSnip.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition == totalItemCount - 1) {
                    loadMoreListener?.onLoadMore(mAdapter.getLastId())
                }

            }
        })
    }

    fun getItemCount() = mAdapter.itemCount

    fun addItem(item: UnboxingSectoralUIKitModel) {
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

    fun addDatas(datas: List<UnboxingSectoralUIKitModel>) {
        val itemsToAdd = mutableListOf<UnboxingSectoralUIKitModel>()
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
        fun onLoadMore(lastId: Int)
    }

}