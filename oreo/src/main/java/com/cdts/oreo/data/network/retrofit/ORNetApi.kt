package com.cdts.oreo.data.network.retrofit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.webkit.MimeTypeMap
import com.cdts.oreo.config.ORConfig
import com.cdts.oreo.data.network.ORError
import com.cdts.oreo.data.network.retrofit.converter.BitmapConverterFactory
import com.cdts.oreo.data.network.retrofit.converter.InputStreamConverterFactory
import com.cdts.oreo.data.network.retrofit.converter.StringConverterFactory
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.*
import java.util.concurrent.TimeUnit


enum class ORRequestType {
    Json, Data, String, Upload, MultipartUpload
}

enum class ORHttpMethod {
    GET, POST, PUT, DELETE
}

data class ORUploadMultipartFile(var file: File, var name: String, var fileName: String, var mimeType: String)

interface ORNetApiUploadMultipartProtocol {
    var files: List<ORUploadMultipartFile>?
}

interface ORNetApiImageProtocol {
    var bitmap: Bitmap?
}

private interface ORApiInterface {
    @GET
    fun get(@Url url: String, @QueryMap map: Map<String, String>): Observable<Any>

    @POST
    @FormUrlEncoded
    fun post(@Url url: String, @FieldMap map: Map<String, String>): Observable<Any>

    @PUT
    @FormUrlEncoded
    fun put(@Url url: String, @FieldMap map: Map<String, String>): Observable<Any>

    @DELETE
    fun delete(@Url url: String, @QueryMap map: Map<String, String>): Observable<Any>

    @Multipart
    @POST
    fun multipart(@Url url: String, @PartMap body: Map<String, @JvmSuppressWildcards RequestBody>, @Part files: List<MultipartBody.Part>): Observable<@JvmSuppressWildcards Any>
}


abstract class ORNetApiModel {
    @Transient
    open var identifier: String = UUID.randomUUID().toString()
    @Transient
    open var url: String = ""
    @Transient
    open var params: MutableMap<String, String> = mutableMapOf()
    @Transient
    open var method: ORHttpMethod = ORHttpMethod.GET

    @Transient
    var responseData: Any? = null
    @Transient
    var error: ORError? = null

    open fun fill(data: Any) {}

    @Suppress("UNCHECKED_CAST")
    fun<T> setIndicator(indicator: ORIndicatorProtocol?, context: Context, text: String): T {
        ORNetIndicatorClient.add(this, indicator, context, text)
        return this as T
    }

}

abstract class ORNetApi {
    abstract var baseUrlString: String
    open var baseHeaders: Map<String, String>? = null
    open var baseParams: Map<String, String>? = null
    open var timeoutIntervalForRead: Long = 45
    open var timeoutIntervalForWrite: Long = 45
    open var timeoutIntervalForConnect: Long = 45

    private val okHttpClient: OkHttpClient by lazy {
        var builder = OkHttpClient.Builder()
            .readTimeout(timeoutIntervalForRead, TimeUnit.SECONDS)
            .writeTimeout(timeoutIntervalForWrite, TimeUnit.SECONDS)
            .connectTimeout(timeoutIntervalForConnect, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                var request = chain.request()
                baseHeaders?.let {
                    var builder = request.newBuilder()
                    for((k, v) in it) {
                        builder = builder.addHeader(k, v)
                    }
                    request = builder.method(request.method(), request.body()).build()
                }
                baseParams?.let {
                    var builder = request.url().newBuilder()
                    for((k, v) in it) {
                        builder = builder.addQueryParameter(k, v)
                    }
                    request = request.newBuilder().url(builder.build()).build()
                }
                request = adapt(request)
                val response = chain.proceed(request)
                adapt(response)
            }

        if (!ORConfig.isRelease) {
            val logger = HttpLoggingInterceptor {
                Timber.tag("HTTP").i(it)
            }
            logger.level = HttpLoggingInterceptor.Level.BODY
            builder = builder.addInterceptor(logger)
        }
        builder.build()
    }

    open fun adapt(request: Request): Request {
        return request
    }
    open fun adapt(response: Response): Response {
        if (!response.isSuccessful) {
            throw ORError(response.code(), response.body()?.string() ?: "")
        }
        return response
    }
    open fun adapt(url: String): String {
        return url
    }
    open fun<T> adapt(signal: Observable<T>): Observable<T> {
        return signal
    }

    private fun getDataRequest(format: ORRequestType): Retrofit {
        val factory: Converter.Factory = when {
            this is ORNetApiImageProtocol -> BitmapConverterFactory.create()
            format == ORRequestType.Data -> InputStreamConverterFactory.create()
            format == ORRequestType.String -> StringConverterFactory.create()
            else -> GsonConverterFactory.create()
        }
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(factory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrlString)
            .build()
    }

    @Suppress("UNCHECKED_CAST")
    fun<T: ORNetApiModel> signal(api: T, format: ORRequestType = ORRequestType.Json): Observable<T> {
        if (api.error != null) {
            return Observable.error(api.error)
        }
        val request = getDataRequest(format).create(ORApiInterface::class.java)
        val requestUrl = adapt(api.url)
        val observer = when (api.method) {
            ORHttpMethod.GET -> request.get(requestUrl, api.params)
            ORHttpMethod.POST -> {
                if (api is ORNetApiUploadMultipartProtocol) {
                    request.multipart(requestUrl, getMultipartBody(api), getMultipartBodyParts(api))
                } else {
                    request.post(requestUrl, api.params)
                }
            }
            ORHttpMethod.PUT -> request.put(requestUrl, api.params)
            ORHttpMethod.DELETE -> request.delete(requestUrl, api.params)
        }
        ORNetClient.add(api)
        ORNetIndicatorClient.show(api)
        val ob: Observable<T> = observer.subscribeOn(Schedulers.io()).flatMap {
            ORNetClient.remove(api)
            ORNetIndicatorClient.hide(api)
            ORNetIndicatorClient.remove(api)
            when (format) {
                ORRequestType.Json -> {
                    getModelFromResponse(api, it).doOnNext {
                            model ->
                        model.responseData = it
                        model.fill(it)
                    }
                }
                ORRequestType.Data -> {
                    if (api is ORNetApiImageProtocol) {
                        val bytes = getBytes(it as InputStream)
                        api.bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    }
                    api.responseData = it
                    api.fill(it)
                    Observable.just(api)
                }
                else -> {
                    api.responseData = it
                    api.fill(it)
                    Observable.just(api)
                }
            }
        }.doOnError {
            ORNetClient.remove(api)
            ORNetIndicatorClient.hide(api)
            ORNetIndicatorClient.remove(api)
            Timber.e("[OR][NetApi] 请求失败：$it")
        }.observeOn(AndroidSchedulers.mainThread())
        return adapt(ob)
    }
    private fun<T: ORNetApiModel> getModelFromResponse(api: T, it: Any): Observable<T> {
        return Observable.create<T> { sink ->
            var json = it as? String
            if(json == null) {
                json = Gson().toJson(it)
            }
            val model = Gson().fromJson(json, api.javaClass)
            sink.onNext(model)
            sink.onComplete()
        }.onErrorResumeNext { _: Throwable ->
            Observable.just(api)
        }
    }

    private fun getBytes(inStream: InputStream): ByteArray {
        val outStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len = inStream.read(buffer)
        while (len != -1) {
            outStream.write(buffer, 0, len)
            len = inStream.read(buffer)
        }
        outStream.close()
        inStream.close()
        return outStream.toByteArray()
    }

    private fun getMultipartBody(api: ORNetApiModel): Map<String, RequestBody> {
        val body = mutableMapOf<String, RequestBody>()
        api.params.forEach { (key, value) ->
            body[key] = RequestBody.create(MediaType.parse("multipart/form-data"), value)
        }
        return body
    }

    private fun getMultipartBodyParts(api: ORNetApiUploadMultipartProtocol): List<MultipartBody.Part> {
        val parts = mutableListOf<MultipartBody.Part>()
        api.files?.forEach { file ->
            val part = createMultipartBodyPart(file)
            parts.add(part)
        }
        return parts
    }

    private fun createMultipartBodyPart(model: ORUploadMultipartFile): MultipartBody.Part {
        val extension = MimeTypeMap.getFileExtensionFromUrl(model.file.toURI().toString())
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)!!
        val fileBody = RequestBody.create(MediaType.parse(mimeType), model.file)
        return MultipartBody.Part.createFormData(model.name, model.fileName, fileBody)
    }
}