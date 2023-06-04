package com.feylabs.uikit.listcomponent.unboxingstock

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.feylabs.core.helper.view.ViewUtils.gone
import com.feylabs.core.helper.view.ViewUtils.visible
import com.feylabs.uikit.R
import com.feylabs.uikit.databinding.CustomUikitListUnboxingStockBinding
import com.feylabs.uikit.listcomponent.uikitmodel.GenerateDummyData
import com.feylabs.uikit.listcomponent.uikitmodel.UnboxingSectoralUIKitModel
import com.feylabs.uikit.util.RecyclerViewUtil.setHorizontalLayoutManager
import com.feylabs.uikit.util.RecyclerViewUtil.setStaggeredGridLayoutManager
import com.feylabs.uikit.util.RecyclerViewUtil.setVerticalLayoutManager

class UIKitUnboxingStockList : ConstraintLayout {

    enum class LayoutType {
        LINEAR_VERTICAL,
        LINEAR_HORIZONTAL,
        GRID_2,//STAGGERED
    }

    private val binding: CustomUikitListUnboxingStockBinding =
        CustomUikitListUnboxingStockBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    lateinit var onClickInterface : OnUnboxingStockListOnClickInterface


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

            //loadDummyData()
        }
    }

    var layoutType: LayoutType = LayoutType.GRID_2
        get() = field
        set(value) {
            setUILayoutType(value)
            field = value
        }

    fun showSkeleton() {
        when (layoutType) {
            LayoutType.LINEAR_VERTICAL -> {
                binding.skeletonListUnboxingStockSingle.visible()
                binding.skeletonListUnboxingStock.gone()
            }
            LayoutType.LINEAR_HORIZONTAL -> {
                binding.skeletonListUnboxingStockSingle.visible()
                binding.skeletonListUnboxingStock.gone()
            }
            LayoutType.GRID_2 -> {
                binding.skeletonListUnboxingStock.visible()
                binding.skeletonListUnboxingStockSingle.gone()
            }
        }
    }

    fun hideSkeleton() {
        binding.skeletonListUnboxingStock.root.visibility = View.GONE
        binding.skeletonListUnboxingStockSingle.root.visibility = View.GONE
    }

    private fun setUILayoutType(value: LayoutType) {
        binding.rvUikitListUnboxingStock.apply {
            when (value) {
                LayoutType.LINEAR_VERTICAL -> {
                    mAdapter.isGrid = false
                    this.setVerticalLayoutManager(context)
                }
                LayoutType.LINEAR_HORIZONTAL -> {
                    mAdapter.isGrid = false
                    this.setHorizontalLayoutManager(context = context)
                }
                LayoutType.GRID_2 -> {
                    mAdapter.isGrid = true
                    this.setStaggeredGridLayoutManager(
                        2, true
                    )
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
                if(::onClickInterface.isInitialized){
                    onClickInterface.onClick(data.volume)
                }
            }
        })
    }

    fun setClickInterface(mInterface : OnUnboxingStockListOnClickInterface){
        this.onClickInterface = mInterface
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

        if (mAdapter.itemCount == 0) {
            showSkeleton()
        } else {
            hideSkeleton()
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
            showSkeleton()
        } else {
            hideSkeleton()
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
                layoutType = LayoutType.GRID_2
            }
            if (layoutTypeOrdinal == 1) {
                layoutType = LayoutType.LINEAR_VERTICAL
            }
            if (layoutTypeOrdinal == 2) {
                layoutType = LayoutType.LINEAR_HORIZONTAL
            }
        } finally {
            styledAttrs.recycle()
        }
        initRecyclerView()
        initAdapterClick()
        showSkeleton()
    }
}

interface OnUnboxingStockListOnClickInterface{
    fun onClick(volume:String)
}