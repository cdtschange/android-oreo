package com.cdts.oreo.data.network.retrofit.converter

import android.graphics.Bitmap
import android.graphics.BitmapFactory

import java.lang.reflect.Type

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

class BitmapConverterFactory private constructor() : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, Bitmap>? {
        return if (type === Bitmap::class.java) {
            Converter { value -> BitmapFactory.decodeStream(value.byteStream()) }
        } else {
            null
        }
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>,
                                      methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        return null
    }

    companion object {

        fun create(): BitmapConverterFactory {
            return BitmapConverterFactory()
        }
    }
}