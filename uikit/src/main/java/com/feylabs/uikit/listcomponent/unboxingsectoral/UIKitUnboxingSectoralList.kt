package com.feylabs.uikit.listcomponent.unboxingsectoral

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.feylabs.uikit.databinding.CustomUikitListUnboxingSectoralBinding
import com.feylabs.uikit.util.RecyclerViewUtil.setHorizontalLayoutManager

class UIKitUnboxingSectoralList : ConstraintLayout {

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
            loadDummyData()
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
        binding.rvUikitListUnboxingSectoral.apply {
            this.adapter = mAdapter
            setHorizontalLayoutManager(context)
        }
    }

    private fun getItemCount() = mAdapter.itemCount

    private fun addItem(item: UnboxingSectoralUIKitModel) {
        val position = mAdapter.itemCount
        mAdapter.data.add(item)
        mAdapter.notifyItemInserted(position)
    }

    private fun addDatas(datas: List<UnboxingSectoralUIKitModel>) {
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