package com.feylabs.uikit.util

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

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

    fun RecyclerView.setStaggeredGridLayoutManager(
        spanCount: Int = 2,
        isVertical: Boolean,
    ) {
        layoutManager = StaggeredGridLayoutManager(
            spanCount,
            if (isVertical) StaggeredGridLayoutManager.VERTICAL else StaggeredGridLayoutManager.HORIZONTAL,
        )
    }

    fun RecyclerView.setGridLayoutManager(
        context: Context,
        spanCount: Int,
        isVertical: Boolean,
        isReverse: Boolean = false,
    ) {
        layoutManager = GridLayoutManager(
            context,
            spanCount,
            if (isVertical) GridLayoutManager.VERTICAL else GridLayoutManager.HORIZONTAL,
            isReverse
        )
    }

}