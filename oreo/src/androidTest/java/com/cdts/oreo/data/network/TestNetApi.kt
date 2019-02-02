package com.cdts.oreo.data.network

import android.graphics.Bitmap
import com.cdts.oreo.data.network.retrofit.*


object NetApiTestConstant {
    val urlString = "https://httpbin.org/"
    val imageUrlString = "https://avatars0.githubusercontent.com/"
    val urlNotExistString = "https://invalid-url-here.org/this/does/not/exist"
    val baseHeaders = mapOf("H1" to "hv1")
    val baseParams = mapOf("k1" to "v1")
    val params = mutableMapOf("k2" to "v2")
    val unicodeParams = mutableMapOf(
        "french" to "français",
        "japanese" to "日本語",
        "arabic" to "العربية",
        "emoji" to "😃"
    )
}

open class TestNetApi: ORNetApi() {
    override var baseUrlString: String = ""
    override var baseHeaders: Map<String, String>? = NetApiTestConstant.baseHeaders
    override var baseParams: Map<String, String>? = NetApiTestConstant.baseParams
}
open class TestNetApiModel: ORNetApiModel() {
    var result: Map<String, Any>? = null
    override fun fill(data: Any) {
        (data as? Map<String, Any>)?.let {
            fill(it)
            return
        }
    }
    open fun fill(map: Map<String, Any>) {
        result = map
    }
}
class TestStringNetApiModel: TestNetApiModel() {
    var contentString: String? = null

    override fun fill(data: Any) {
        contentString = data as? String
    }
}
class TestDataNetApiModel: TestNetApiModel(), ORNetApiImageProtocol {
    override var bitmap: Bitmap? = null
}

class TestUploadMultipartNetApiModel: TestNetApiModel(), ORNetApiUploadMultipartProtocol {
    override var files: List<ORUploadMultipartFile>? = null
}