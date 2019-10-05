package com.rabross.minecraftskintools.extractor

import android.graphics.Bitmap
import com.rabross.minecraftskintools.Section

fun Bitmap.extractSection(section: Section) = Bitmap.createBitmap(this, section.x, section.y, section.width, section.height)