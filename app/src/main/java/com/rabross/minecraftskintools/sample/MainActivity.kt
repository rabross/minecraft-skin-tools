package com.rabross.minecraftskintools.sample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.rabross.minecraftskintools.R
import com.rabross.minecraftskintools.Section
import com.rabross.minecraftskintools.SectionHD
import com.rabross.minecraftskintools.extractor.extractSection
import com.rabross.minecraftskintools.extractor.extractSectionHD
import com.rabross.minecraftskintools.renderer.avatar
import com.rabross.minecraftskintools.renderer.draw
import com.rabross.minecraftskintools.renderer.renderFull
import com.rabross.minecraftskintools.renderer.renderPrimary
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val skinTexture = bitmapFromAsset(this, "xxxx.png")

        imageView1.setImageBitmapOnReady { renderFull(skinTexture, width, width * 2) }
        imageView2.setImageBitmapOnReady { renderPrimary(skinTexture, width, width * 2) }
        imageView4.setImageBitmapOnReady { head(skinTexture, width, width) }
        imageView3.setImageBitmapOnReady { custom(skinTexture, width, height) }
    }
}

private fun head(skinTexture: Bitmap, width: Int, height: Int): Bitmap {

    val head = if (skinTexture.width == 64){
        skinTexture.extractSection(Section.Head())
    } else skinTexture.extractSectionHD(SectionHD.Head())

    val headOverlay = if (skinTexture.width == 64){
        skinTexture.extractSection(Section.HeadOverlay())
    }else skinTexture.extractSectionHD(SectionHD.Head())

    Canvas(head).drawBitmap(headOverlay, 0f, 0f, null)
    return Bitmap.createScaledBitmap(head, width, height, false)
        .also { head.recycle() }
}

private fun custom(skinTexture: Bitmap, width: Int, height: Int): Bitmap {

    val head = if (skinTexture.width == 64){
        skinTexture.extractSection(Section.Head())
    }else skinTexture.extractSectionHD(SectionHD.Head())

    val body = if (skinTexture.width == 64){
        skinTexture.extractSection(Section.Body())
    }else skinTexture.extractSectionHD(SectionHD.Body())

    val bodyOverlay = if (skinTexture.width == 64){
        skinTexture.extractSection(Section.BodyOverlay())
    }else skinTexture.extractSectionHD(SectionHD.BodyOverlay())

    val leftLeg = if (skinTexture.width == 64){
        skinTexture.extractSection(Section.LegLeftOverlay())
    }else skinTexture.extractSectionHD(SectionHD.LegLeftOverlay())

    val rightArm = if (skinTexture.width == 64){
        skinTexture.extractSection(Section.ArmRightOverlay())
    }else skinTexture.extractSectionHD(SectionHD.ArmRightOverlay())

    val avatarWidth = rightArm.width + body.width
    val avatarHeight = head.height + body.height + leftLeg.height

    val renderingBitmap = Bitmap.createBitmap(avatarWidth, avatarHeight, Bitmap.Config.ARGB_8888)

    avatar {
        withHead(head)
        withBody(body)
        withBody(bodyOverlay)
        withLeftLeg(leftLeg)
        withRightArm(rightArm)
    }.draw(Canvas(renderingBitmap))

    return renderingBitmap.scaleRetainAspectRatio(width, height)
        .also { renderingBitmap.recycle() }
}

private fun bitmapFromAsset(context: Context, filePath: String): Bitmap {
    val inputStream = context.assets.open(filePath)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream.close()
    return bitmap!!
}

private fun Bitmap.scaleRetainAspectRatio(destWidth: Int, destHeight: Int): Bitmap {
    return when {
        width > height -> Bitmap.createScaledBitmap(
            this,
            destWidth,
            height * destWidth / width,
            false
        )
        else -> Bitmap.createScaledBitmap(this, width * destHeight / height, destHeight, false)
    }
}

private fun ImageView.setImageBitmapOnReady(func: ImageView.() -> Bitmap) {
    post { setImageBitmap(func()) }
}
