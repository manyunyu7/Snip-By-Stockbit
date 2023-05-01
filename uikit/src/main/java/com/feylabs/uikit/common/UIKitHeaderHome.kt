package com.feylabs.uikit.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.feylabs.uikit.databinding.CustomUikitHomeHeaderBinding

class UIKitHeaderHome : ConstraintLayout {

    private val binding: CustomUikitHomeHeaderBinding = CustomUikitHomeHeaderBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


}