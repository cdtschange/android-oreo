package com.cdts.oreo.data.network

import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.data.network.retrofit.ORNetClient
import com.cdts.oreo.data.network.retrofit.ORRequestType
import org.junit.Test


class ORNetApiResponseTests: BaseTestCase() {

//    @Test
    fun testResponseJson() {

        val apiModel = TestNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        api.baseHeaders = NetApiTestConstant.baseHeaders
        api.baseParams = NetApiTestConstant.baseParams
        apiModel.url = "get"
        apiModel.params = NetApiTestConstant.params
        assert(ORNetClient.runningApis().count() == 0)
        api.signal(apiModel).subscribe({ data ->
            assert(ORNetClient.runningApis().count() == 0)
            assert(apiModel.responseData != null)
            val headers = data.result!!["headers"] as Map<String, String>
            NetApiTestConstant.baseHeaders.forEach {
                assert(headers[it.key] == it.value)
            }
            val params = data.result!!["args"] as Map<String, String>
            assert(params.count() == NetApiTestConstant.baseParams.count() + NetApiTestConstant.params.count())
            assert(params[NetApiTestConstant.baseParams.keys.first()] == NetApiTestConstant.baseParams.values.first())
            assert(params[NetApiTestConstant.params.keys.first()] == NetApiTestConstant.params.values.first())

            signal()
        }, {
            assert(false)
            signal()
        })

        await()
    }

//    @Test
    fun testResponseString() {

        val apiModel = TestStringNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        api.baseHeaders = NetApiTestConstant.baseHeaders
        api.baseParams = NetApiTestConstant.baseParams
        apiModel.url = "get"
        apiModel.params = NetApiTestConstant.params
        assert(ORNetClient.runningApis().count() == 0)
        api.signal(apiModel, ORRequestType.String).subscribe({ data ->
            assert(ORNetClient.runningApis().count() == 0)
            assert(apiModel.responseData != null)
            assert(data.contentString != null)

            signal()
        }, {
            assert(false)
            signal()
        })

        await()
    }

//    @Test
    fun testResponseData() {

        val apiModel = TestDataNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.imageUrlString
        api.baseHeaders = NetApiTestConstant.baseHeaders
        api.baseParams = NetApiTestConstant.baseParams
        apiModel.url = "u/883027"
        apiModel.params = NetApiTestConstant.params
        assert(ORNetClient.runningApis().count() == 0)
        api.signal(apiModel, ORRequestType.Data).subscribe({ data ->
            assert(ORNetClient.runningApis().count() == 0)
            assert(apiModel.responseData != null)
            assert(data.bitmap != null)

            signal()
        }, {
            assert(false)
            signal()
        })

        await()
    }

}
