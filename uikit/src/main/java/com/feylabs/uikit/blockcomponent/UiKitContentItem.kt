package com.feylabs.uikit.blockcomponent

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.feylabs.uikit.databinding.CustomUikitInfoCardBinding
import com.feylabs.uikit.databinding.CustomUikitSnipMenuHomeBinding
import com.feylabs.uikit.util.ImageViewUtil.loadImageFromURL

class UiKitContentItem : ConstraintLayout {

    constructor(context: Context) : super(context){
    }

    constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet)
    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrSet,
        defStyleAttr
    )

    private var menuSnipAction: (() -> Unit)? = null
    private var menuAcademyAction: (() -> Unit)? = null
    private var menuUnboxingAction: (() -> Unit)? = null
    private var menuEventAction: (() -> Unit)? = null


    private val binding = CustomUikitSnipMenuHomeBinding.inflate(LayoutInflater.from(context))

    init { // inflate binding and add as view
        addView(binding.root)
        binding.btnAcademy.setOnClickListener {
            menuAcademyAction?.invoke()
        }

        binding.btnSnip.setOnClickListener {
            menuSnipAction?.invoke()
        }

        binding.btnEvent.setOnClickListener {
            menuEventAction?.invoke()
        }

        binding.btnUnboxing.setOnClickListener {
            menuUnboxingAction?.invoke()
        }
    }

    fun onMenuSnipClick(action: (() -> Unit)? = null){
        this.menuSnipAction = action
    }

    fun onMenuAcademyClick(action: (() -> Unit)? = null){
        this.menuAcademyAction = action
    }

    fun onMenuUnboxingClick(action: (() -> Unit)? = null){
        this.menuUnboxingAction = action
    }

    fun onMenuEventClick(action: (() -> Unit)? = null){
        this.menuEventAction = action
    }

}