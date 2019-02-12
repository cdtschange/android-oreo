package com.cdts.oreo.data.network

import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.data.network.cookie.ORCookie
import com.cdts.oreo.data.network.retrofit.ORNetClient
import com.cdts.oreo.extension.queryDictionary
import okhttp3.Request
import org.junit.Test

class ORNetApiRequestTests: BaseTestCase() {


//    @Test
    fun testRequestGet() {

        class GetTestNetApi: TestNetApi() {
            override fun adapt(request: Request): Request {
                assert(request.method() == "GET")
                assert(request.url().url().path == "/get")
                assert(request.url().url().toString().startsWith(NetApiTestConstant.urlString))
                val query = request.url().query()!!.queryDictionary()
                assert(query.count() == NetApiTestConstant.baseParams.count() + NetApiTestConstant.params.count())
                assert(query[NetApiTestConstant.baseParams.keys.first()] == NetApiTestConstant.baseParams.values.first())
                assert(query[NetApiTestConstant.params.keys.first()] == NetApiTestConstant.params.values.first())
                assert(request.headers().size() == NetApiTestConstant.baseHeaders.count())
                assert(request.headers()[NetApiTestConstant.baseHeaders.keys.first()] == NetApiTestConstant.baseHeaders.values.first())
                return super.adapt(request)
            }
        }

        val apiModel = TestNetApiModel()
        val api = GetTestNetApi()
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


            val apiModel2 = TestNetApiModel()
            assert(ORNetClient.runningApis().count() == 0)
            api.signal(apiModel2).subscribe({ data ->
                assert(ORNetClient.runningApis().count() == 0)
                signal()
            }, {
                assert(false)
                signal()
            }, {}, {
                assert(ORNetClient.runningApis().count() == 1)
                assert(ORNetClient.runningApis().first() == apiModel2)
            })
        }, {
            assert(false)
            signal()
        }, {}, {
            assert(ORNetClient.runningApis().count() == 1)
            assert(ORNetClient.runningApis().first() == apiModel)

        })

        await()
    }


//    @Test
    fun testRequestPost() {

        class PostTestNetApi: TestNetApi() {
            override fun adapt(request: Request): Request {
                assert(request.method() == "POST")
                assert(request.url().url().path == "/post")
                assert(request.url().url().toString().startsWith(NetApiTestConstant.urlString))
                assert(request.headers().size() == NetApiTestConstant.baseHeaders.count())
                assert(request.headers()[NetApiTestConstant.baseHeaders.keys.first()] == NetApiTestConstant.baseHeaders.values.first())
                return super.adapt(request)
            }
        }

        val apiModel = TestNetApiModel()
        val api = PostTestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        api.baseHeaders = NetApiTestConstant.baseHeaders
        api.baseParams = NetApiTestConstant.baseParams
        apiModel.url = "post"
        apiModel.params = NetApiTestConstant.params
        assert(ORNetClient.runningApis().count() == 0)
        api.signal(apiModel).subscribe({ data ->
            assert(ORNetClient.runningApis().count() == 0)
            assert(apiModel.responseData != null)
            val headers = data.result!!["headers"] as Map<String, String>
            NetApiTestConstant.baseHeaders.forEach {
                assert(headers[it.key] == it.value)
            }
            val params = data.result!!["form"] as Map<String, String>
            assert(params.count() == NetApiTestConstant.baseParams.count() + NetApiTestConstant.params.count())
            assert(params[NetApiTestConstant.baseParams.keys.first()] == NetApiTestConstant.baseParams.values.first())
            assert(params[NetApiTestConstant.params.keys.first()] == NetApiTestConstant.params.values.first())

            signal()
        }, {
            assert(false)
            signal()
        }, {}, {
            assert(ORNetClient.runningApis().count() == 1)
            assert(ORNetClient.runningApis().first() == apiModel)

        })

        await()
    }

//    @Test
    fun testClearCookie() {
        ORCookie.clear()
        ORCookie.cookieArray = arrayOf()
        assert(ORCookie.cookieArray.count() == 0)
        ORCookie.cookieArray = arrayOf("a=1")
        assert(ORCookie.cookieArray.count() == 1)
        assert(ORCookie.cookieArray.first() == "a=1")
        ORCookie.cookieArray = arrayOf("a=2")
        assert(ORCookie.cookieArray.count() == 1)
        assert(ORCookie.cookieArray.first() == "a=2")
        ORCookie.cookieArray = arrayOf("b=3")
        assert(ORCookie.cookieArray.count() == 2)
        ORCookie.cookieArray = arrayOf("abcd")
        assert(ORCookie.cookieArray.count() == 3)
        ORCookie.clear()
        assert(ORCookie.cookieArray.count() == 0)
    }

}