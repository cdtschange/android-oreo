package com.cdts.oreo.data.network

import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.data.network.retrofit.ORNetApi
import com.cdts.oreo.data.network.retrofit.ORNetApiModel
import com.cdts.oreo.data.network.retrofit.ORRequestType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test


class TORNetApiOtherTests: BaseTestCase() {

    @Test
    fun testNetApiWithoutFill() {

        val apiModel = TestNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        apiModel.url = "get"
        api.signal(apiModel).subscribe({
            assert(true)
            signal()
        }, {
            assert(false)
            signal()
        })

        await()
    }

    @Test
    fun testNetApiError() {

        val statusCode = ORStatusCode.BadRequest.value
        val errorMessage = "Test Error"
        val apiModel = TestNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        apiModel.error = ORError(statusCode, errorMessage)
        apiModel.url = "get"
        api.signal(apiModel).subscribe({
            assert(false)
        }, { error ->
            assert(error is ORError)
            assert((error as ORError).statusCode == statusCode)
            assert((error).message == errorMessage)
        })
    }

    open class NoBaseTestNetApi: ORNetApi() {
        override var baseUrlString: String = ""
    }
    open class NoBaseTestNetApiModel: ORNetApiModel()

    @Test
    fun testNetApiBase() {

        val apiModel = NoBaseTestNetApiModel()
        val api = NoBaseTestNetApi()
        assert(api.timeoutIntervalForRead == 45L)
        assert(api.timeoutIntervalForWrite == 45L)
        assert(api.timeoutIntervalForConnect == 45L)
        api.baseUrlString = NetApiTestConstant.urlString
        apiModel.url = "get"
        api.signal(apiModel).subscribe({
            assert(it.error == null)
            it.fill("a")
            signal()
        }, {
            assert(false)
            signal()
        })

        await()
    }

    @Test
    fun testRequestJsonCancel() {

        val apiModel = TestNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        apiModel.url = "get"
        api.signal(apiModel, ORRequestType.Json).subscribe({
            assert(false)
        }, {
            assert(false)
        }, {
            assert(false)
        }, { dispose ->
            dispose.dispose()
            GlobalScope.launch {
                delay(2000L)
                signal()
            }
        })

        await()
    }

    @Test
    fun testRequestStringCancel() {

        val apiModel = TestStringNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        apiModel.url = "get"
        api.signal(apiModel, ORRequestType.String).subscribe({
            assert(false)
        }, {
            assert(false)
        }, {
            assert(false)
        }, { dispose ->
            dispose.dispose()
            GlobalScope.launch {
                delay(2000L)
                signal()
            }
        })

        await()
    }

    @Test
    fun testRequestDataCancel() {

        val apiModel = TestDataNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        apiModel.url = "get"
        api.signal(apiModel, ORRequestType.Data).subscribe({
            assert(false)
        }, {
            assert(false)
        }, {
            assert(false)
        }, { dispose ->
            dispose.dispose()
            GlobalScope.launch {
                delay(2000L)
                signal()
            }
        })

        await()
    }

    @Test
    fun testRequestMultipartUploadError() {

        val apiModel = TestUploadMultipartNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        apiModel.url = "get"
        api.signal(apiModel, ORRequestType.MultipartUpload).subscribe({
            assert(false)
        }, {
            assert(false)
        }, {
            assert(false)
        }, { dispose ->
            dispose.dispose()
            GlobalScope.launch {
                delay(2000L)
                signal()
            }
        })

        await()
    }
}
