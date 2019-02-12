package com.cdts.oreo.data.network

import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.data.network.retrofit.ORRequestType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test


class TORNetApiOtherTests: BaseTestCase() {

//    @Test
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

//    @Test
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

//    @Test
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

//    @Test
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

//    @Test
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
