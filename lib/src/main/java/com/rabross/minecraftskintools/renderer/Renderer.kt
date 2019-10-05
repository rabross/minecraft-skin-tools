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

fun avatar(func: RenderBuilder.() -> Unit): List<Part> {
    return RenderBuilder().run {
        func()
        build()
    }
}

fun List<Part>.draw(canvas: Canvas) {
    forEach {
        it.draw(canvas)
        it.recycle()
    }
}

class RenderBuilder {

    private var parts = ArrayList<Part>()

    fun withHead(bitmap: Bitmap) {
        parts.add(Part.Head(bitmap))
    }

    fun withBody(bitmap: Bitmap) {
        parts.add(Part.Body(bitmap))
    }

    fun withRightArm(bitmap: Bitmap) {
        parts.add(Part.ArmRight(bitmap))
    }

    fun withLeftArm(bitmap: Bitmap) {
        parts.add(Part.ArmLeft(bitmap))
    }

    fun withRightLeg(bitmap: Bitmap) {
        parts.add(Part.LegRight(bitmap))
    }

    fun withLeftLeg(bitmap: Bitmap) {
        parts.add(Part.LegLeft(bitmap))
    }

    fun build() = parts
}