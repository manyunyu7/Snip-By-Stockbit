package com.feylabs.uikit.listcomponent.movie_genre

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.feylabs.uikit.R
import com.feylabs.uikit.databinding.CustomUikitListMovieGenresBinding
import com.feylabs.uikit.listcomponent.uikitmodel.MovieGenreUIKitModel
import com.feylabs.uikit.util.RecyclerViewUtil.setGridLayoutManager
import com.feylabs.uikit.util.RecyclerViewUtil.setHorizontalLayoutManager
import com.feylabs.uikit.util.RecyclerViewUtil.setStaggeredGridLayoutManager

class UIKitUnboxingMovieGenreList : ConstraintLayout {

    private val skeletonCounts: Int = 3
    private val binding: CustomUikitListMovieGenresBinding =
        CustomUikitListMovieGenresBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    lateinit var onClickInterface : MovieGenreItemAdapter.ItemInterface


    private val mAdapter by lazy { MovieGenreItemAdapter() }

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
    }

    fun showSkeleton(count: Int = skeletonCounts) {
        val parentLayout = binding.uikitListUnboxingMovieSkeletonContainer
        for (i in 1..count) {
            val include = LayoutInflater.from(parentLayout.context)
                .inflate(R.layout.uikit_skeleton_unboxing_sectoral_item, parentLayout, false)
            parentLayout.addView(include)
        }
        binding.uikitListUnboxingMovieSkeletonContainer.visibility = View.VISIBLE
        binding.rvUikitListUnboxingSectoral.visibility = View.GONE
    }

    fun hideSkeleton() {
        val parentLayout = binding.uikitListUnboxingMovieSkeletonContainer
        parentLayout.removeAllViews()
        binding.uikitListUnboxingMovieSkeletonContainer.visibility = View.GONE
        binding.rvUikitListUnboxingSectoral.visibility = View.VISIBLE
    }

    private fun initAdapterClick() {
        mAdapter.setInterface(object : MovieGenreItemAdapter.ItemInterface {

            override fun onClick(string: String) {
                if(::onClickInterface.isInitialized){
                    onClickInterface.onClick(string)
                }
            }
        })
    }

    fun setClickInterface(mInterface : MovieGenreItemAdapter.ItemInterface){
        this.onClickInterface = mInterface
    }

    private fun initRecyclerView() {
        binding.rvUikitListUnboxingSectoral.apply {
            this.adapter = mAdapter
            setStaggeredGridLayoutManager(3,false)
        }
    }

    private fun getItemCount() = mAdapter.itemCount

    fun addItem(item: MovieGenreUIKitModel) {
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

    fun addDatas(datas: List<MovieGenreUIKitModel>) {
        val itemsToAdd = mutableListOf<MovieGenreUIKitModel>()
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
            showSkeleton(skeletonCounts)
        } else {
            hideSkeleton()
        }
    }

    fun onUnboxingItemClick(action: (() -> Unit)? = null) {
        if (action != null) {
        }
    }


    interface OnUnboxingSectoralListOnClickInterface{
        fun onClick(volume:String)
    }

}