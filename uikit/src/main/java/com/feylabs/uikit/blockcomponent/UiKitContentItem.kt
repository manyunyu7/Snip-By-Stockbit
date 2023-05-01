package com.feylabs.uikit.blockcomponent

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.feylabs.uikit.databinding.CustomUikitContentItemBinding
import com.feylabs.uikit.util.ImageViewUtil.loadImageFromURL

class UiKitContentItem : ConstraintLayout {

    constructor(context: Context) : super(context)

    private var onActionClick: (() -> Unit) = {}


    constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet)
    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrSet,
        defStyleAttr
    )


    private val binding: CustomUikitContentItemBinding = CustomUikitContentItemBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        binding.root.setOnClickListener {
            onActionClick.invoke()
        }
    }

    var title: CharSequence
        set(value) {
            binding.tvMain.text = value
        }
        get() = binding.tvMain.text?.toString() ?: ""


    var subtitle: CharSequence
        set(value) {
            binding.tvSubtitle.text = value
        }
        get() = binding.tvSubtitle.text?.toString() ?: ""


    val thumbnailImage
        get() = binding.ivMainImage


    fun setThumbnail(imageUrl: String?) {
        binding.ivMainImage.loadImageFromURL(
            context, imageUrl
        )
    }

    fun setSubtitleIcon(imageUrl: String?) {
        binding.ivTitleIcon.loadImageFromURL(
            context, imageUrl
        )
    }

    fun onClickListener(action: (() -> Unit)? = null) {
        if (action != null) {
            onActionClick = action
        }
    }


}