package com.feylabs.uikit.blockcomponent

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.uikit.R
import com.feylabs.uikit.databinding.CustomUikitSectionMenuBinding

class UIKitSectionMenu : ConstraintLayout {

    private var onSeeAllClick: (() -> Unit) = { showToast(context, "See All Section") }

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

    private val binding: CustomUikitSectionMenuBinding = CustomUikitSectionMenuBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        binding.tvUikitSectionSeeAll.setOnClickListener {
            onSeeAllClick.invoke()
        }
    }

    var sectionTitle: CharSequence
        set(value) {
            binding.tvUikitSectionTitle.text = value
        }
        get() = binding.tvUikitSectionTitle.text?.toString() ?: ""


    var sectionSeeAll: CharSequence
        set(value) {
            binding.tvUikitSectionSeeAll.text = value
        }
        get() = binding.tvUikitSectionSeeAll.text?.toString() ?: ""

    var sectionTitleFont: Typeface? = null
        set(value) {
            binding.tvUikitSectionTitle.typeface = value
            field = value
        }

    var sectionSeeAllFont: Typeface? = null
        set(value) {
            binding.tvUikitSectionSeeAll.typeface = value
            field = value
        }


    fun onClickListener(action: (() -> Unit)? = null) {
        if (action != null) {
            onSeeAllClick = action
        }
    }

    private fun extractCustomAttributes(attrSet: AttributeSet) {
        val styledAttrs = context.obtainStyledAttributes(
            attrSet, R.styleable.UIKitSectionMenu
        )
        try {
            sectionTitle = styledAttrs.getString(R.styleable.UIKitSectionMenu_sectionTitle) ?: ""
            sectionSeeAll = styledAttrs.getString(R.styleable.UIKitSectionMenu_sectionSeeAll) ?: ""
            sectionTitleFont = ResourcesCompat.getFont(context, R.font.inter_semi_bold)
            sectionSeeAllFont = ResourcesCompat.getFont(context, R.font.inter_regular)
        } finally {
            styledAttrs.recycle()
        }
    }


}