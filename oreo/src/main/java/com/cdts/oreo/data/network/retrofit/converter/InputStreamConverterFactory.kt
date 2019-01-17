package com.cdts.oreo.data.network.retrofit.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.InputStream
import java.lang.reflect.Type

class InputStreamConverterFactory private constructor() : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, InputStream>? {
        return Converter { value -> value.byteStream() }
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>,
                                      methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        return null
    }

    companion object {

        fun create(): InputStreamConverterFactory {
            return InputStreamConverterFactory()
        }
    }
}