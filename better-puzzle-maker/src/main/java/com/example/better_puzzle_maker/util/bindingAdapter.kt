package com.example.better_puzzle_maker.util

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide



@BindingAdapter("bind:loadImage")
internal fun ImageView.loadImage(image: Bitmap?){
    Glide.with(this.context)
        .load(image)
        .into(this)
}