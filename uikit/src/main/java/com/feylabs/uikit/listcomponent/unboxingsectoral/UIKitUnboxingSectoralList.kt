package com.feylabs.uikit.listcomponent.unboxingsectoral

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.feylabs.uikit.R
import com.feylabs.uikit.databinding.CustomUikitListUnboxingSectoralBinding
import com.feylabs.uikit.listcomponent.uikitmodel.GenerateDummyData
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.uikit.util.RecyclerViewUtil.setHorizontalLayoutManager

class UIKitUnboxingSectoralList : ConstraintLayout {

    private val skeletonCounts: Int = 3
    private val binding: CustomUikitListUnboxingSectoralBinding =
        CustomUikitListUnboxingSectoralBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    private var onActionClick: (() -> Unit) = {}


    private val mAdapter by lazy { UnboxingItemAdapter() }

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

    fun showSkeleton(count: Int = skeletonCounts) {
        val parentLayout = binding.uikitListUnboxingSectoralSkeletonContainer
        for (i in 1..count) {
            val include = LayoutInflater.from(parentLayout.context)
                .inflate(R.layout.uikit_skeleton_unboxing_sectoral_item, parentLayout, false)
            parentLayout.addView(include)
        }
        binding.uikitListUnboxingSectoralSkeletonContainer.visibility = View.VISIBLE
        binding.rvUikitListUnboxingSectoral.visibility = View.GONE
    }

    fun hideSkeleton() {
        val parentLayout = binding.uikitListUnboxingSectoralSkeletonContainer
        parentLayout.removeAllViews()
        binding.uikitListUnboxingSectoralSkeletonContainer.visibility = View.GONE
        binding.rvUikitListUnboxingSectoral.visibility = View.VISIBLE
    }

    private fun initAdapterClick() {
        mAdapter.setInterface(object : UnboxingItemAdapter.ItemInterface {
            override fun onClick(data: UnboxingSectoralUIKitModel) {
                onActionClick.invoke()
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvUikitListUnboxingSectoral.apply {
            this.adapter = mAdapter
            setHorizontalLayoutManager(context)
        }
    }

    private fun getItemCount() = mAdapter.itemCount

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
            showSkeleton(skeletonCounts)
        } else {
            hideSkeleton()
        }
    }

    fun onUnboxingItemClick(action: (() -> Unit)? = null) {
        if (action != null) {
            onActionClick = action
        }
    }

}