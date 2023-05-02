package com.feylabs.uikit.util

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewUtil {

    fun RecyclerView.setHorizontalLayoutManager(context: Context, isHorizontal: Boolean = true) {
        layoutManager = LinearLayoutManager(
            context,
            if (isHorizontal) LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL,
            false
        )
    }

    fun RecyclerView.setVerticalLayoutManager(context: Context, isReverse: Boolean = false) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, isReverse)
    }

    fun RecyclerView.setGridLayoutManager(context: Context, spanCount: Int) {
        layoutManager = GridLayoutManager(context, spanCount)
    }

    fun RecyclerView.setGridLayout(
        context: Context,
        spanCount: Int,
        isVertical: Boolean,
        isReverse: Boolean
    ) {
        layoutManager = GridLayoutManager(
            context,
            spanCount,
            if (isVertical) GridLayoutManager.VERTICAL else GridLayoutManager.HORIZONTAL,
            isReverse
        )
    }

}