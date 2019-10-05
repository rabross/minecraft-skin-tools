package com.rabross.minecraftskintools.renderer

import android.graphics.Bitmap
import android.graphics.Canvas
import com.rabross.minecraftskintools.Part
import com.rabross.minecraftskintools.Section
import com.rabross.minecraftskintools.extractor.extractSection
import java.util.*

fun obtainRenderingBitmap(): Bitmap {
    return Bitmap.createBitmap(16, 32, Bitmap.Config.ARGB_8888)
}

fun renderFull(skinTexture: Bitmap, width: Int = 16, height: Int = 32): Bitmap {

    val fullRender = obtainRenderingBitmap()

    avatar {
        with(skinTexture) {
            withHead(extractSection(Section.Head()))
            withBody(extractSection(Section.Body()))
            withRightArm(extractSection(Section.ArmRight()))
            withLeftArm(extractSection(Section.ArmLeft()))
            withRightLeg(extractSection(Section.LegRight()))
            withLeftLeg(extractSection(Section.LegLeft()))
            withHead(extractSection(Section.HeadOverlay()))
            withBody(extractSection(Section.BodyOverlay()))
            withRightArm(extractSection(Section.ArmRightOverlay()))
            withLeftArm(extractSection(Section.ArmLeftOverlay()))
            withRightLeg(extractSection(Section.LegRightOverlay()))
            withLeftLeg(extractSection(Section.LegLeftOverlay()))
        }
    }.draw(Canvas(fullRender))

    return Bitmap.createScaledBitmap(fullRender, width, height, false).also { fullRender.recycle() }
}

fun renderPrimary(skinTexture: Bitmap, width: Int = 16, height: Int = 32): Bitmap {

    val fullRender = obtainRenderingBitmap()

    avatar {
        with(skinTexture) {
            withHead(extractSection(Section.Head()))
            withBody(extractSection(Section.Body()))
            withRightArm(extractSection(Section.ArmRight()))
            withLeftArm(extractSection(Section.ArmLeft()))
            withRightLeg(extractSection(Section.LegRight()))
            withLeftLeg(extractSection(Section.LegLeft()))
        }
    }.draw(Canvas(fullRender))

    return Bitmap.createScaledBitmap(fullRender, width, height, false).also { fullRender.recycle() }
}

fun List<DrawPart>.draw(canvas: Canvas) {
    forEach {
        it.draw(canvas)
        it.recycle()
    }
}

fun avatar(func: RenderBuilder.() -> Unit): List<DrawPart> {
    return RenderBuilder().run {
        func()
        build()
    }
}

class RenderBuilder {

    private var parts = ArrayList<PartBuilder>()

    fun withHead(bitmap: Bitmap) {
        parts.add(PartBuilder.Head(bitmap))
    }

    fun withBody(bitmap: Bitmap) {
        parts.add(PartBuilder.Body(bitmap))
    }

    fun withRightArm(bitmap: Bitmap) {
        parts.add(PartBuilder.ArmRight(bitmap))
    }

    fun withLeftArm(bitmap: Bitmap) {
        parts.add(PartBuilder.ArmLeft(bitmap))
    }

    fun withRightLeg(bitmap: Bitmap) {
        parts.add(PartBuilder.LegRight(bitmap))
    }

    fun withLeftLeg(bitmap: Bitmap) {
        parts.add(PartBuilder.LegLeft(bitmap))
    }

    fun build(): List<DrawPart> {
        return parts.map { it.build() }
    }
}

sealed class PartBuilder(private val bitmap: Bitmap) {

    fun build(): DrawPart {
        return when (this) {
            is Head -> DrawPart.Head(Part.Head(bitmap))
            is Body -> DrawPart.Body(Part.Body(bitmap))
            is ArmRight -> DrawPart.ArmRight(Part.ArmRight(bitmap))
            is ArmLeft -> DrawPart.ArmLeft(Part.ArmLeft(bitmap))
            is LegRight -> DrawPart.LegRight(Part.LegRight(bitmap))
            is LegLeft -> DrawPart.LegLeft(Part.LegLeft(bitmap))
        }
    }

    class Head(bitmap: Bitmap) : PartBuilder(bitmap)
    class Body(bitmap: Bitmap) : PartBuilder(bitmap)
    class ArmRight(bitmap: Bitmap) : PartBuilder(bitmap)
    class ArmLeft(bitmap: Bitmap) : PartBuilder(bitmap)
    class LegRight(bitmap: Bitmap) : PartBuilder(bitmap)
    class LegLeft(bitmap: Bitmap) : PartBuilder(bitmap)
}

sealed class DrawPart(private val bitmap: Bitmap) {

    abstract fun draw(canvas: Canvas)

    fun recycle() {
        bitmap.recycle()
    }

    protected fun Canvas.drawPart(part: Part) {
        with(part) { drawBitmap(bitmap, left, top, null) }
    }

    data class Head(val part: Part) : DrawPart(part.bitmap) {
        override fun draw(canvas: Canvas) {
            canvas.drawPart(part)
        }
    }

    data class Body(val part: Part) : DrawPart(part.bitmap) {
        override fun draw(canvas: Canvas) {
            canvas.drawPart(part)
        }
    }

    data class ArmRight(val part: Part) : DrawPart(part.bitmap) {
        override fun draw(canvas: Canvas) {
            canvas.drawPart(part)
        }
    }

    data class ArmLeft(val part: Part) : DrawPart(part.bitmap) {
        override fun draw(canvas: Canvas) {
            canvas.drawPart(part)
        }
    }

    data class LegRight(val part: Part) : DrawPart(part.bitmap) {
        override fun draw(canvas: Canvas) {
            canvas.drawPart(part)
        }
    }

    data class LegLeft(val part: Part) : DrawPart(part.bitmap) {
        override fun draw(canvas: Canvas) {
            canvas.drawPart(part)
        }
    }
}