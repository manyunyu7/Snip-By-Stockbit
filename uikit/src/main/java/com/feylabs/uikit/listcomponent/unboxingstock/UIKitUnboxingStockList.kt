package com.feylabs.uikit.listcomponent.unboxingstock

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.feylabs.uikit.R
import com.feylabs.uikit.databinding.CustomUikitListUnboxingStockBinding
import com.feylabs.uikit.listcomponent.uikitmodel.GenerateDummyData
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.uikit.util.RecyclerViewUtil.setHorizontalLayoutManager
import com.feylabs.uikit.util.RecyclerViewUtil.setStaggeredGridLayoutManager
import com.feylabs.uikit.util.RecyclerViewUtil.setVerticalLayoutManager

class UIKitUnboxingStockList : ConstraintLayout {

    enum class LayoutType {
        VERTICAL_SCROLL,
        HORIZONTAL_SCROLL,
        GRID,//STAGGERED
    }

    private val binding: CustomUikitListUnboxingStockBinding =
        CustomUikitListUnboxingStockBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    private var onActionClick: (() -> Unit) = {}


    private val mAdapter by lazy { UnboxingItemAdapter() }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        extractCustomAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        extractCustomAttributes(attrs)
    }

    init {
        if (isInEditMode.not()) {
            initRecyclerView()
            initAdapterClick()
            //loadDummyData()
        }
    }

    var layoutType: LayoutType = LayoutType.GRID
        set(value) {
            setUILayoutType(value)
        }
        get() {
            return field
        }

    private fun setUILayoutType(value: LayoutType) {
        binding.rvUikitListUnboxingStock.apply {
            when (value) {
                LayoutType.GRID -> {
                    mAdapter.isGrid = true
                    this.setStaggeredGridLayoutManager(
                        2, true
                    )
                }
                LayoutType.VERTICAL_SCROLL -> {
                    mAdapter.isGrid = false
                    this.setVerticalLayoutManager(context)
                }
                LayoutType.HORIZONTAL_SCROLL -> {
                    mAdapter.isGrid = false
                    this.setHorizontalLayoutManager(context = context)
                }
            }
        }

    }


    fun loadDummyData() {
        addDatas(GenerateDummyData.createData())
    }

    private fun initAdapterClick() {
        mAdapter.setInterface(object : UnboxingItemAdapter.ItemInterface {
            override fun onClick(data: UnboxingSectoralUIKitModel) {
                onActionClick.invoke()
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvUikitListUnboxingStock.apply {
            this.adapter = mAdapter
            mAdapter.isGrid =
                this.layoutManager is GridLayoutManager || this.layoutManager is StaggeredGridLayoutManager
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
    }

    fun onUnboxingItemClick(action: (() -> Unit)? = null) {
        if (action != null) {
            onActionClick = action
        }
    }

    private fun extractCustomAttributes(attrSet: AttributeSet?) {
        val styledAttrs = context.obtainStyledAttributes(
            attrSet, R.styleable.UIKitUnboxingStockList
        )
        try {
            val layoutTypeOrdinal =
                styledAttrs.getInt(R.styleable.UIKitUnboxingStockList_layoutStyle, 1)

            if (layoutTypeOrdinal == 0) {
                layoutType = LayoutType.GRID
            }
            if (layoutTypeOrdinal == 1) {
                layoutType = LayoutType.HORIZONTAL_SCROLL
            }
            if (layoutTypeOrdinal == 2) {
                layoutType = LayoutType.VERTICAL_SCROLL
            }
        } finally {
            styledAttrs.recycle()
        }
    }

}