package com.rabross.minecraftskintools.extractor

import android.graphics.Bitmap
import com.rabross.minecraftskintools.Section
import com.rabross.minecraftskintools.SectionHD

fun Bitmap.extractSection(section: Section): Bitmap = Bitmap.createBitmap(this, section.x, section.y, section.width, section.height)
fun Bitmap.extractSectionHD(section: SectionHD): Bitmap = Bitmap.createBitmap(this, section.x, section.y, section.width, section.height)