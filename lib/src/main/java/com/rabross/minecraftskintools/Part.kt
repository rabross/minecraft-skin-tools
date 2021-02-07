package com.rabross.minecraftskintools

import android.graphics.Bitmap
import android.graphics.Canvas

/**
 * A bitmap of a particular Minecraft skin texture section that can draw itself
 */
sealed class Part(private val bitmap: Bitmap, private val left: Float, private val top: Float) {
    class Head(headBitmap: Bitmap) : Part(headBitmap, if (headBitmap.width == 8) 4f else 10f, if (headBitmap.width == 8) 0f else 4f)
    class Body(bodyBitmap: Bitmap) : Part(bodyBitmap, if (bodyBitmap.width == 8) 4f else 10f, if (bodyBitmap.width == 8) 8f else 20f)
    class ArmRight(rightArmBitmap: Bitmap) : Part(rightArmBitmap, if (rightArmBitmap.width == 4) 0f else 2f, if (rightArmBitmap.width == 4) 8f else 20f)
    class ArmLeft(leftArmBitmap: Bitmap) : Part(leftArmBitmap, if (leftArmBitmap.width == 4) 12f else 26f, if (leftArmBitmap.width == 4) 8f else 20f)
    class LegRight(rightLegBitmap: Bitmap) : Part(rightLegBitmap, if (rightLegBitmap.width == 4) 4f else 10f, if (rightLegBitmap.width == 4) 20f else 44f)
    class LegLeft(leftLegBitmap: Bitmap) : Part(leftLegBitmap, if (leftLegBitmap.width == 4) 8f else 18f, if (leftLegBitmap.width == 4) 20f else 44f)

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