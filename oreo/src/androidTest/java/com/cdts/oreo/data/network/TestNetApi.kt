package com.cdts.oreo.data.network

import android.graphics.Bitmap
import com.cdts.oreo.data.network.retrofit.*


object NetApiTestConstant {
    val urlString = "https://httpbin.org/"
    val imageUrlString = "https://avatars0.githubusercontent.com/"
    val urlNotExistString = "https://invalid-url-here.org/this/does/not/exist"
    val baseHeaders = mapOf("H1" to "hv1")
    val headers = mapOf("H2" to "hv2")
    val baseParams = mapOf("k1" to "v1")
    val params = mapOf("k2" to "v2")
    val unicodeParams = mapOf(
        "french" to "français",
        "japanese" to "日本語",
        "arabic" to "العربية",
        "emoji" to "😃"
    )
}

class TestNetApi: ORNetApi() {
    override var baseUrlString: String = ""
    override var baseHeaders: Map<String, String>? = NetApiTestConstant.baseHeaders
    override var baseParams: Map<String, String>? = NetApiTestConstant.baseParams
}
class TestNetApiModel: ORNetApiModel() {

}
class TestStringNetApiModel: ORNetApiModel() {
    var contentString: String? = null

    override fun fill(data: Any) {
        contentString = data as? String
    }
}
class TestDataNetApiModel: ORNetApiModel(), ORNetApiImageProtocol {
    override var bitmap: Bitmap? = null
}

class TestUploadMultipartNetApiModel: ORNetApiModel(), ORNetApiUploadMultipartProtocol {
    override var files: Array<ORUploadMultipartFile>? = null
}