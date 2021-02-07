package com.rabross.minecraftskintools.renderer

import android.graphics.Bitmap
import android.graphics.Canvas
import com.rabross.minecraftskintools.Part
import com.rabross.minecraftskintools.Section
import com.rabross.minecraftskintools.SectionHD
import com.rabross.minecraftskintools.extractor.extractSection
import com.rabross.minecraftskintools.extractor.extractSectionHD
import java.util.*

fun obtainRenderingBitmap(width: Int): Bitmap {
    return if (width == 64) {
        Bitmap.createBitmap(16, 32, Bitmap.Config.ARGB_8888)
    }else Bitmap.createBitmap(36, 70, Bitmap.Config.ARGB_8888)
}

fun renderFull(skinTexture: Bitmap, width: Int = 16, height: Int = 32): Bitmap {

    val fullRender = obtainRenderingBitmap(skinTexture.width)

    if (skinTexture.width == 64) {
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
    }else {
        avatar {
            with(skinTexture) {
                withHead(extractSectionHD(SectionHD.Head()))
                withBody(extractSectionHD(SectionHD.Body()))
                withRightArm(extractSectionHD(SectionHD.ArmRight()))
                withLeftArm(extractSectionHD(SectionHD.ArmLeft()))
                withRightLeg(extractSectionHD(SectionHD.LegRight()))
                withLeftLeg(extractSectionHD(SectionHD.LegLeft()))
                withHead(extractSectionHD(SectionHD.HeadOverlay()))
                withBody(extractSectionHD(SectionHD.BodyOverlay()))
                withRightArm(extractSectionHD(SectionHD.ArmRightOverlay()))
                withLeftArm(extractSectionHD(SectionHD.ArmLeftOverlay()))
                withRightLeg(extractSectionHD(SectionHD.LegRightOverlay()))
                withLeftLeg(extractSectionHD(SectionHD.LegLeftOverlay()))
            }
        }.draw(Canvas(fullRender))
    }

    return Bitmap.createScaledBitmap(fullRender, width, height, false).also { fullRender.recycle() }
}

fun renderPrimary(skinTexture: Bitmap, width: Int = 16, height: Int = 32): Bitmap {

    val fullRender = obtainRenderingBitmap(skinTexture.width)

    if (skinTexture.width == 64){
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
    }else {
        avatar {
            with(skinTexture) {
                withHead(extractSectionHD(SectionHD.Head()))
                withBody(extractSectionHD(SectionHD.Body()))
                withRightArm(extractSectionHD(SectionHD.ArmRight()))
                withLeftArm(extractSectionHD(SectionHD.ArmLeft()))
                withRightLeg(extractSectionHD(SectionHD.LegRight()))
                withLeftLeg(extractSectionHD(SectionHD.LegLeft()))
            }
        }.draw(Canvas(fullRender))
    }

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