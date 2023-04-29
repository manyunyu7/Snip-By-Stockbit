package com.feylabs.uikit.blockcomponent

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.feylabs.uikit.databinding.CustomUikitInfoCardBinding
import com.feylabs.uikit.util.ImageViewUtil.loadImageFromURL

class UIKitInfoCard : ConstraintLayout {

    constructor(context: Context) : super(context){
    }

    constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet)
    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrSet,
        defStyleAttr
    )

    private val binding = CustomUikitInfoCardBinding.inflate(LayoutInflater.from(context))

    init { // inflate binding and add as view
        addView(binding.root)
    }


    var title: CharSequence
        set(value) {
            binding.tvTitle.text = value
        }
        get() = binding.tvTitle.text?.toString() ?: ""

    var content: CharSequence
        set(value) {
            binding.tvContent.text = value
        }
        get() = binding.tvContent.text?.toString() ?: ""

    var subtitle: CharSequence
        set(value) {
            binding.tvSecondary.text = value
        }
        get() = binding.tvSecondary.text?.toString() ?: ""


    val thumbnailImage
        get() = binding.ivThumbnail


    fun setImage(imageUrl: String?) {
       binding.ivThumbnail.loadImageFromURL(
           context,imageUrl
       )
    }

}