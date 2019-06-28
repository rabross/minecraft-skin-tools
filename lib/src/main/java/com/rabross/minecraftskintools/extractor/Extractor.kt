package com.rabross.minecraftskintools.extractor

import android.graphics.Bitmap

fun Bitmap.extractHead(): Bitmap = Bitmap.createBitmap(this, 8, 8, 8, 8)
fun Bitmap.extractBody(): Bitmap = Bitmap.createBitmap(this, 20, 20, 8, 12)
fun Bitmap.extractRightArm(): Bitmap = Bitmap.createBitmap(this, 44, 20, 4, 12)
fun Bitmap.extractLeftArm(): Bitmap = Bitmap.createBitmap(this, 36, 52, 4, 12)
fun Bitmap.extractRightLeg(): Bitmap = Bitmap.createBitmap(this, 4, 20, 4, 12)
fun Bitmap.extractLeftLeg(): Bitmap = Bitmap.createBitmap(this, 20, 52, 4, 12)
fun Bitmap.extractHeadOverlay(): Bitmap = Bitmap.createBitmap(this, 40, 8, 8, 8)
fun Bitmap.extractBodyOverlay(): Bitmap = Bitmap.createBitmap(this, 20, 36, 8, 12)
fun Bitmap.extractRightArmOverlay(): Bitmap = Bitmap.createBitmap(this, 44, 36, 4, 12)
fun Bitmap.extractLeftArmOverlay(): Bitmap = Bitmap.createBitmap(this, 52, 52, 4, 12)
fun Bitmap.extractRightLegOverlay(): Bitmap = Bitmap.createBitmap(this, 4, 36, 4, 12)
fun Bitmap.extractLeftLegOverlay(): Bitmap = Bitmap.createBitmap(this, 4, 52, 4, 12)