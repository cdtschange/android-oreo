package com.cdts.demo.data.network.data

import com.cdts.oreo.data.network.retrofit.*

class NetworkApi: ORNetApi() {
    override var baseUrlString: String = "https://httpbin.org"
    override var baseHeaders: Map<String, String>? = mapOf("base" to "header from base")
    override var baseParams: Map<String, String>? = mapOf("base" to "params from base")
}

open class BaseNetApiModel: ORNetApiModel() {
    var result: Map<String, Any>? = null
    override fun fill(data: Any) {
        (data as? Map<String, Any>)?.let {
            fill(it)
            return
        }
    }
    fun fill(map: Map<String, Any>) {
        result = map
    }
}

class GetNetApiModel: BaseNetApiModel() {
    override var url: String = "/get"
    override var params: MutableMap<String, String> = mutableMapOf("param" to "abc")
}
class PostNetApiModel: BaseNetApiModel() {
    override var url: String = "/post"
    override var method: ORHttpMethod = ORHttpMethod.POST
    override var params: MutableMap<String, String> = mutableMapOf("param" to "abc")
}

class PutNetApiModel: BaseNetApiModel() {
    override var url: String = "/put"
    override var method: ORHttpMethod = ORHttpMethod.PUT
    override var params: MutableMap<String, String> = mutableMapOf("param" to "abc")
}

class DeleteNetApiModel: BaseNetApiModel() {
    override var url: String = "/delete"
    override var method: ORHttpMethod = ORHttpMethod.DELETE
    override var params: MutableMap<String, String> = mutableMapOf("param" to "abc")
}

class MultipartUploadNetApiModel: BaseNetApiModel(), ORNetApiUploadMultipartProtocol {
    override var files: List<ORUploadMultipartFile>? = null
    override var url: String = "/post"
    override var method: ORHttpMethod = ORHttpMethod.POST
    override var params: MutableMap<String, String> = mutableMapOf("param" to "abc")
}