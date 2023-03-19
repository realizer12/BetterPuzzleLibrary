package com.example.betterandcompany.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.betterandcompany.R


//애니메이션 주면서 bundle 값 과 같이  navigation 이동할때 사용
fun NavController.navigateWithAnim(destinationId: Int, bundle: Bundle? = null){
    val options = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_from_right)
        .setExitAnim(R.anim.stationary)
        .setPopEnterAnim(R.anim.stationary)
        .setPopExitAnim(R.anim.slide_to_right)
        .build()
    this.navigate(destinationId,bundle,options)
}

fun Uri.uriToBitmap(context: Context): Bitmap =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(context.contentResolver, this)
        ) { decoder: ImageDecoder, _: ImageDecoder.ImageInfo?, _: ImageDecoder.Source? ->
            decoder.isMutableRequired = true
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
        }
    } else {
        BitmapDrawable(
            context.resources,
            MediaStore.Images.Media.getBitmap(context.contentResolver, this)
        ).bitmap
    }

fun ImageView.loadImage(image: Bitmap?, loadFinished:(boolean:Boolean)->Unit){
    Glide.with(this.context)
        .load(image)
        .centerCrop()
        .listener(object :RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadFinished(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                loadFinished(true)
                return false
            }
        }).into(this)
}