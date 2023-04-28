package com.feylabs.uikit.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.feylabs.uikit.R


object ImageViewUtil {

    fun ImageView.loadImageFromURL(
        context: Context,
        url: String? = "",
        //thumbnailsType: ThumbnailsType = ThumbnailsType.LOADING_1
    ) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.placeholder_snip_light)
            //.diskCacheStrategy(DiskCacheStrategy.NONE)
            //.skipMemoryCache(true)
            .into(this)
    }

    fun ImageView.loadImage(
        context: Context,
        drawable: Int,
        //thumbnailsType: ThumbnailsType = ThumbnailsType.LOADING_1
    ) {
        Glide.with(context)
            .load(drawable)
            //.placeholder(thumbnailsType.value)
            //.thumbnail(Glide.with(context).load(R.raw.ic_loading_google).fitCenter())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(this)
    }


}