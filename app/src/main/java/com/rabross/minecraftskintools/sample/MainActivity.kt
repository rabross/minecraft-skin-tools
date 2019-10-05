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
import com.rabross.minecraftskintools.extractor.extractSection
import com.rabross.minecraftskintools.renderer.avatar
import com.rabross.minecraftskintools.renderer.draw
import com.rabross.minecraftskintools.renderer.renderFull
import com.rabross.minecraftskintools.renderer.renderPrimary
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val skinTexture = bitmapFromAsset(this, "semitransparenttest.png")

        imageView1.setImageBitmapOnReady { renderFull(skinTexture, width, width * 2) }
        imageView2.setImageBitmapOnReady { renderPrimary(skinTexture, width, width * 2) }
        imageView4.setImageBitmapOnReady { head(skinTexture, width, width) }
        imageView3.setImageBitmapOnReady { custom(skinTexture, width, height) }
    }
}

private fun head(skinTexture: Bitmap, width: Int, height: Int): Bitmap {
    val head = skinTexture.extractSection(Section.Head())
    val headOverlay = skinTexture.extractSection(Section.HeadOverlay())
    Canvas(head).drawBitmap(headOverlay, 0f, 0f, null)
    return Bitmap.createScaledBitmap(head, width, height, false)
        .also { head.recycle() }
}

private fun custom(skinTexture: Bitmap, width: Int, height: Int): Bitmap {

    val head = skinTexture.extractSection(Section.Head())
    val body = skinTexture.extractSection(Section.Body())
    val bodyOverlay = skinTexture.extractSection(Section.BodyOverlay())
    val leftLeg = skinTexture.extractSection(Section.LegLeftOverlay())
    val rightArm = skinTexture.extractSection(Section.ArmRightOverlay())

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
