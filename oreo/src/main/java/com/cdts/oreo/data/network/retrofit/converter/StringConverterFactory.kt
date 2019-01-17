package com.cdts.oreo.data.network.retrofit.converter

import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class StringConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *> {
        return Converter<ResponseBody, String> { value -> value.string() }
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody> {
        return Converter<String, RequestBody> { value -> RequestBody.create(MEDIA_TYPE, value) }
    }

    companion object {
        private val MEDIA_TYPE = MediaType.parse("text/plain")
        fun create(): StringConverterFactory {
            return StringConverterFactory()
        }
    }
}
