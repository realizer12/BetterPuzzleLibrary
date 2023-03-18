package com.example.better_puzzle_maker.util

import android.graphics.Canvas
import android.graphics.Point
import android.view.View

/**
 * drag and drop 시  drag shadow의 크기를 키워서 - hover 효과를 줌.
**/
internal class ScaledDragShadow(view: View, private val scale: Float): View.DragShadowBuilder(view) {

    override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {

        val scaledWidth = (view.width * scale).toInt()
        val scaledHeight = (view.height * scale).toInt()
        outShadowSize?.set(scaledWidth, scaledHeight)

        outShadowTouchPoint?.set(scaledWidth / 2, scaledHeight / 2)
    }
    override fun onDrawShadow(canvas: Canvas?) {
        canvas?.scale(
            scale,
            scale
        )
        view.draw(canvas)
    }
}
