package com.feylabs.uikit.blockcomponent

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.feylabs.uikit.R
import com.feylabs.uikit.databinding.CustomUikitLargeInfoCardBinding
import com.feylabs.uikit.util.ImageViewUtil.loadImageFromURL


class UIKitLargeInfoCard : ConstraintLayout {

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) {
        extractCustomAttributes(attrSet)
    }

    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrSet,
        defStyleAttr
    ) {
        extractCustomAttributes(attrSet)
    }

    private val binding = CustomUikitLargeInfoCardBinding.inflate(LayoutInflater.from(context))
    init {
        addView(binding.root)
    }

    private fun extractCustomAttributes(attrSet: AttributeSet) {
        val styledAttrs = context.obtainStyledAttributes(
            attrSet, R.styleable.UIKitLargeInfoCard
        )
        try {
            cardTitle = styledAttrs.getString(R.styleable.UIKitLargeInfoCard_cardTitle) ?: ""
            cardBody = styledAttrs.getString(R.styleable.UIKitLargeInfoCard_cardBody) ?: ""
            cardSubtitle =
                styledAttrs.getString(R.styleable.UIKitLargeInfoCard_cardSubtitle) ?: ""
            cardImage = styledAttrs.getString(R.styleable.UIKitLargeInfoCard_cardImage)
            cardTitleFont = styledAttrs.getString(R.styleable.UIKitLargeInfoCard_cardTitleFont)
                ?.let { Typeface.create(it, Typeface.BOLD) }
            cardBodyFont =
                styledAttrs.getString(R.styleable.UIKitLargeInfoCard_cardBodyFont)
                    ?.let { Typeface.create(it, Typeface.NORMAL) }
            cardSubtitleFont =
                styledAttrs.getString(R.styleable.UIKitLargeInfoCard_cardSubtitleFont)
                    ?.let { Typeface.create(it, Typeface.NORMAL) }
            cardTitleTextColor =
                styledAttrs.getColor(R.styleable.UIKitLargeInfoCard_cardTitleTextColor, Color.BLACK)
            cardBodyTextColor =
                styledAttrs.getColor(R.styleable.UIKitLargeInfoCard_cardBodyTextColor, Color.BLACK)
            cardSubtitleTextColor = styledAttrs.getColor(
                R.styleable.UIKitLargeInfoCard_cardSubtitleTextColor,
                Color.WHITE
            )
        } finally {
            styledAttrs.recycle()
        }
    }


    var cardTitle: CharSequence
        set(value) {
            binding.tvTitle.text = value
        }
        get() = binding.tvTitle.text?.toString() ?: ""

    var cardBody: CharSequence
        set(value) {
            binding.tvBody.text = value
        }
        get() = binding.tvBody.text?.toString() ?: ""

    var cardSubtitle: CharSequence = ""
        set(value) {
            binding.tvSecondary.text = value
            field = value
        }

    var cardImage: String? = null
        set(value) {
            binding.ivThumbnail.loadImageFromURL(context, value)
        }

    var cardTitleFont: Typeface? = null
        set(value) {
            binding.tvTitle.typeface = value
            field = value
        }

    var cardBodyFont: Typeface? = null
        set(value) {
            binding.tvBody.typeface = value
            field = value
        }

    var cardSubtitleFont: Typeface? = null
        set(value) {
            binding.tvSecondary.typeface = value
            field = value
        }

    var cardTitleTextColor: Int = Color.BLACK
        set(value) {
            binding.tvTitle.setTextColor(value)
        }

    var cardBodyTextColor: Int = Color.BLACK
        set(value) {
            binding.tvBody.setTextColor(value)
        }

    var cardSubtitleTextColor: Int = Color.WHITE
        set(value) {
            binding.tvSecondary.setTextColor(value)
        }


}