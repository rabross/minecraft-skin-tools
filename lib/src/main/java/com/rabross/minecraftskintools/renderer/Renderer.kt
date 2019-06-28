package com.rabross.minecraftskintools.renderer

import android.graphics.Bitmap
import android.graphics.Canvas
import com.rabross.minecraftskintools.extractor.*
import java.util.*

fun obtainRenderingBitmap(): Bitmap {
    return Bitmap.createBitmap(16, 32, Bitmap.Config.ARGB_8888)
}

fun renderFull(skinTexture: Bitmap, width: Int = 16, height: Int = 32): Bitmap {

    val fullRender = obtainRenderingBitmap()

    avatar {
        withHead { bitmap = skinTexture.extractHead() }
        withBody { bitmap = skinTexture.extractBody() }
        withRightArm { bitmap = skinTexture.extractRightArm() }
        withLeftArm { bitmap = skinTexture.extractLeftArm() }
        withRightLeg { bitmap = skinTexture.extractRightLeg() }
        withLeftLeg { bitmap = skinTexture.extractLeftLeg() }
        withHead { bitmap = skinTexture.extractHeadOverlay() }
        withBody { bitmap = skinTexture.extractBodyOverlay() }
        withRightArm { bitmap = skinTexture.extractRightArmOverlay() }
        withLeftArm { bitmap = skinTexture.extractLeftArmOverlay() }
        withRightLeg { bitmap = skinTexture.extractRightLegOverlay() }
        withLeftLeg { bitmap = skinTexture.extractLeftLegOverlay() }
    }.draw(Canvas(fullRender))

    return Bitmap.createScaledBitmap(fullRender, width, height, false).also { fullRender.recycle() }
}

fun renderPrimary(skinTexture: Bitmap, width: Int = 16, height: Int = 32): Bitmap {

    val fullRender = obtainRenderingBitmap()

    avatar {
        withHead { bitmap = skinTexture.extractHead() }
        withBody { bitmap = skinTexture.extractBody() }
        withRightArm { bitmap = skinTexture.extractRightArm() }
        withLeftArm { bitmap = skinTexture.extractLeftArm() }
        withRightLeg { bitmap = skinTexture.extractRightLeg() }
        withLeftLeg { bitmap = skinTexture.extractLeftLeg() }
    }.draw(Canvas(fullRender))

    return Bitmap.createScaledBitmap(fullRender, width, height, false).also { fullRender.recycle() }
}

private fun Canvas.drawHead(head: Bitmap) {
    drawBitmap(head, 4f, 0f, null)
}

private fun Canvas.drawBody(body: Bitmap) {
    drawBitmap(body, 4f, 8f, null)
}

private fun Canvas.drawRightArm(rightArm: Bitmap) {
    drawBitmap(rightArm, 0f, 8f, null)
}

private fun Canvas.drawLeftArm(leftArm: Bitmap) {
    drawBitmap(leftArm, 12f, 8f, null)
}

private fun Canvas.drawRightLeg(rightLeg: Bitmap) {
    drawBitmap(rightLeg, 4f, 20f, null)
}

private fun Canvas.drawLeftLeg(leftLeg: Bitmap) {
    drawBitmap(leftLeg, 8f, 20f, null)
}

fun List<Part>.draw(canvas: Canvas) {
    forEach {
        it.draw(canvas)
        it.recycle()
    }
}

fun avatar(func: RenderBuilder.() -> Unit): List<Part> {
    return RenderBuilder().run {
        func()
        build()
    }
}

class RenderBuilder {

    private var parts = ArrayList<PartBuilder>()

    fun withHead(func: PartBuilder.() -> Unit) {
        parts.add(PartBuilder.Head().apply(func))
    }

    fun withBody(func: PartBuilder.() -> Unit) {
        parts.add(PartBuilder.Body().apply(func))
    }

    fun withRightArm(func: PartBuilder.() -> Unit) {
        parts.add(PartBuilder.ArmRight().apply(func))
    }

    fun withLeftArm(func: PartBuilder.() -> Unit) {
        parts.add(PartBuilder.ArmLeft().apply(func))
    }

    fun withRightLeg(func: PartBuilder.() -> Unit) {
        parts.add(PartBuilder.LegRight().apply(func))
    }

    fun withLeftLeg(func: PartBuilder.() -> Unit) {
        parts.add(PartBuilder.LegLeft().apply(func))
    }

    fun build(): List<Part> {
        return parts.map { it.build() }
    }
}

sealed class Part {

    abstract val bitmap: Bitmap
    abstract fun draw(canvas: Canvas)

    fun recycle() {
        bitmap.recycle()
    }

    data class Head(override val bitmap: Bitmap) : Part() {
        override fun draw(canvas: Canvas) {
            canvas.drawHead(bitmap)
        }
    }

    data class Body(override val bitmap: Bitmap) : Part() {
        override fun draw(canvas: Canvas) {
            canvas.drawBody(bitmap)
        }
    }

    data class RightArm(override val bitmap: Bitmap) : Part() {
        override fun draw(canvas: Canvas) {
            canvas.drawRightArm(bitmap)
        }
    }

    data class LeftArm(override val bitmap: Bitmap) : Part() {
        override fun draw(canvas: Canvas) {
            canvas.drawLeftArm(bitmap)
        }
    }

    data class RightLeg(override val bitmap: Bitmap) : Part() {
        override fun draw(canvas: Canvas) {
            canvas.drawRightLeg(bitmap)
        }
    }

    data class LeftLeg(override val bitmap: Bitmap) : Part() {
        override fun draw(canvas: Canvas) {
            canvas.drawLeftLeg(bitmap)
        }
    }
}

sealed class PartBuilder {

    var bitmap: Bitmap? = null

    fun build(): Part {
        return when (this) {
            is Head -> Part.Head(
                bitmap!!
            )
            is Body -> Part.Body(
                bitmap!!
            )
            is ArmRight -> Part.RightArm(
                bitmap!!
            )
            is ArmLeft -> Part.LeftArm(
                bitmap!!
            )
            is LegRight -> Part.RightLeg(
                bitmap!!
            )
            is LegLeft -> Part.LeftLeg(
                bitmap!!
            )
        }
    }

    class Head : PartBuilder()
    class Body : PartBuilder()
    class ArmRight : PartBuilder()
    class ArmLeft : PartBuilder()
    class LegRight : PartBuilder()
    class LegLeft : PartBuilder()
}