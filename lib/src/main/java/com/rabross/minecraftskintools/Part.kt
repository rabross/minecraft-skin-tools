package com.rabross.minecraftskintools

import android.graphics.Bitmap
import android.graphics.Canvas

/**
 * A bitmap of a particular Minecraft skin texture section that can draw itself
 */
sealed class Part(private val bitmap: Bitmap, private val left: Float, private val top: Float) {
    class Head(headBitmap: Bitmap) : Part(headBitmap, 4f, 0f)
    class Body(bodyBitmap: Bitmap) : Part(bodyBitmap, 4f, 8f)
    class ArmRight(rightArmBitmap: Bitmap) : Part(rightArmBitmap, 0f, 8f)
    class ArmLeft(leftArmBitmap: Bitmap) : Part(leftArmBitmap, 12f, 8f)
    class LegRight(rightLegBitmap: Bitmap) : Part(rightLegBitmap, 4f, 20f)
    class LegLeft(leftLegBitmap: Bitmap) : Part(leftLegBitmap, 8f, 20f)

    fun draw(canvas: Canvas) {
        canvas.drawPart()
    }

    fun recycle() {
        bitmap.recycle()
    }

    private fun Canvas.drawPart() {
        drawBitmap(bitmap, left, top, null)
    }
}