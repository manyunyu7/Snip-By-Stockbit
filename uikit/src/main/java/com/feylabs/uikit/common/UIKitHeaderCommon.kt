package com.feylabs.uikit.common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.feylabs.core.helper.toast.ToastHelper.showToast
import com.feylabs.uikit.R
import com.feylabs.uikit.databinding.CustomUikitHeaderCommonBinding

class UIKitHeaderCommon : ConstraintLayout {


    private var onNavigateBackClickListener: (() -> Unit) = {
        showToast(context, "Back")
    }

    private val binding: CustomUikitHeaderCommonBinding = CustomUikitHeaderCommonBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

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

    enum class TitleTextAlignment {
        LEFT,
        CENTER,
        RIGHT
    }


    var showBackNavigation: Boolean
        set(value) {
            if (value) {
                binding.btnBack.visibility = VISIBLE
            } else {
                binding.btnBack.visibility = GONE
            }
        }
        get() = binding.btnBack.isVisible

    var titleAlignment: TitleTextAlignment = TitleTextAlignment.LEFT
        set(value) {
            setTextAlignment(value)
        }
        get() {
            return field
        }

    var title: CharSequence
        set(value) {
            binding.toolbarTitle.text = value
        }
        get() = binding.toolbarTitle.text?.toString() ?: ""


    fun setTextAlignment(alignment: TitleTextAlignment) {
        when (alignment) {
            TitleTextAlignment.LEFT -> {
                binding.toolbarTitle.gravity = Gravity.START
                binding.toolbarTitle.textAlignment = TEXT_ALIGNMENT_TEXT_START
            }
            TitleTextAlignment.CENTER -> {
                binding.toolbarTitle.gravity = Gravity.CENTER
                binding.toolbarTitle.textAlignment = TEXT_ALIGNMENT_CENTER
            }
            TitleTextAlignment.RIGHT -> {
                binding.toolbarTitle.gravity = Gravity.END
                binding.toolbarTitle.textAlignment = TEXT_ALIGNMENT_TEXT_END
            }
        }
    }

    init {
        binding.btnBack.setOnClickListener {
            onNavigateBackClickListener.invoke()
        }
    }


    private fun extractCustomAttributes(attrSet: AttributeSet) {
        val styledAttrs = context.obtainStyledAttributes(
            attrSet, R.styleable.UIKitHeaderCommon
        )
        try {
            title = styledAttrs.getString(R.styleable.UIKitHeaderCommon_toolbarTitle) ?: ""
            showBackNavigation =
                styledAttrs.getBoolean(R.styleable.UIKitHeaderCommon_showBackNavigation, false)
            val titleAlignmentOrdinal =
                styledAttrs.getInt(R.styleable.UIKitHeaderCommon_toolbarTitleAlignment, 1)

            if (titleAlignmentOrdinal == 0) {
                titleAlignment = TitleTextAlignment.LEFT
            }
            if (titleAlignmentOrdinal == 1) {
                titleAlignment = TitleTextAlignment.CENTER
            }
            if (titleAlignmentOrdinal == 2) {
                titleAlignment = TitleTextAlignment.RIGHT
            }

        } finally {
            styledAttrs.recycle()
        }
    }


}