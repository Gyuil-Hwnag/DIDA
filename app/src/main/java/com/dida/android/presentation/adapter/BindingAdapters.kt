package com.dida.android.presentation.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.android.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("image_from_url")
    fun bindImageFromURL(imageView: ImageView, imageURL: String?) {
        Log.d(TAG, "bindImageFromURL: ${imageURL}")
        if (imageURL.isNullOrEmpty().not()) {
            Glide.with(imageView.context)
                .load(imageURL)
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("image_from_url_rounded")
    fun bindImageFromURLRounded(imageView: ImageView, imageURL: String?) {
        Log.d(TAG, "bindImageFromURL: ${imageURL}")
        if (imageURL.isNullOrEmpty().not()) {
            Glide.with(imageView.context)
                .load(imageURL)
                .transform(CenterCrop(), RoundedCorners(50))
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("nft_img_rounded")
    fun bindNftImgRounded(imageView: ImageView, imageURL: String?) {
        Log.d(TAG, "bindImageFromURL: ${imageURL}")
        if (imageURL.isNullOrEmpty().not()) {
            Glide.with(imageView.context)
                .load(imageURL)
                .transform(CenterCrop(), RoundedCorners(30))
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("image_from_url_circle")
    fun bindImageFromURLCircle(imageView: ImageView, imageURL: String?) {
        Log.d(TAG, "bindImageFromURL: ${imageURL}")
        if (imageURL.isNullOrEmpty().not()) {
            Glide.with(imageView.context)
                .load(imageURL)
                .transform(CenterCrop(), RoundedCorners(200))
                .into(imageView)
        }
    }
}