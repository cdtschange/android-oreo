package com.cdts.oreo.extension

import android.graphics.BitmapFactory
import com.cdts.oreo.BaseTestCase
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class BitmapExTests: BaseTestCase() {
    @Test
    fun testBase64String() {
        val inputStream = javaClass.classLoader.getResourceAsStream("drawable/rainbow.jpg")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val string = bitmap.toBase64()
        assert(string.isNotEmpty())
        val image = string.base64ToBitmap()!!
        assert(image.toBase64() == string)
    }
}