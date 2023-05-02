package com.feylabs.uikit.listcomponent.snip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
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

    private fun initRecyclerView() {
        binding.rvUikitListSnip.apply {
            this.adapter = mAdapter
            setVerticalLayoutManager(context)
        }
    }

    private fun getItemCount() = mAdapter.itemCount

    fun addItem(item: UnboxingSectoralUIKitModel) {
        val position = mAdapter.itemCount
        mAdapter.data.add(item)
        mAdapter.notifyItemInserted(position)
    }

    fun addDatas(datas: List<UnboxingSectoralUIKitModel>) {
        val positionStart = mAdapter.itemCount
        mAdapter.data.addAll(datas.toMutableList())
        mAdapter.notifyItemRangeInserted(positionStart, datas.size)
    }

    fun onUnboxingItemClick(action: (() -> Unit)? = null) {
        if (action != null) {
            onActionClick = action
        }
    }

}