package com.cdts.oreo.data.network

import android.app.Activity
import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.data.network.retrofit.ORNetIndicatorClient
import com.cdts.oreo.data.network.retrofit.ORRequestType
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.kaopiz.kprogresshud.KProgressHUD
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class ORNetIndicatorClientTests: BaseTestCase() {

    @Test
    fun testNetIndicator() {
        val appContext: Activity = mock()
        val hud: KProgressHUD = mock()
        doReturn(hud).whenever(hud).setLabel(any())
        doReturn(hud).whenever(hud).setDetailsLabel(any())
        doReturn(hud).whenever(hud).setCustomView(any())
        doReturn(hud).whenever(hud).setStyle(any())
        val indicator = spy(ORIndicator())
        doReturn(hud).whenever(indicator).createHUD(any())
        val indicator2 = spy(ORIndicator())
        doReturn(hud).whenever(indicator2).createHUD(any())

        val apiModel = TestNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        api.baseHeaders = NetApiTestConstant.baseHeaders
        api.baseParams = NetApiTestConstant.baseParams
        apiModel.url = "get"
        apiModel.params = NetApiTestConstant.params

        val text = "Loading"
        api.signal(apiModel.setIndicator(indicator, appContext, text), ORRequestType.Json).subscribe({
            val model = ORNetIndicatorClient.getIndicatorModel(apiModel.identifier)
            assert(model == null)
            val apiModel2 = TestNetApiModel()
            apiModel2.url = "get"
            apiModel2.params = NetApiTestConstant.params
            val text2 = "Loading2"
            api.signal(apiModel2.setIndicator(indicator2, appContext, text2)).subscribe({
            }, {
                assert(false)
                signal()
            }, {
                val model = ORNetIndicatorClient.getIndicatorModel(apiModel.identifier)
                assert(model == null)
                signal()
            }, {
                val model = ORNetIndicatorClient.getIndicatorModel(apiModel.identifier)!!
                assert(model.api === apiModel2)
                assert(model.indicator === indicator2)
                assert(model.context.get() === appContext)
                assert(model.text == text2)
                assert(indicator!!.showing)
            })
        }, {
            assert(false)
            signal()
        }, {
        }, {
            val model = ORNetIndicatorClient.getIndicatorModel(apiModel.identifier)!!
            assert(model.api === apiModel)
            assert(model.indicator === indicator)
            assert(model.context.get() === appContext)
            assert(model.text == text)
            assert(indicator!!.showing)
        })

        await()
    }

    @Test
    fun testNoNetIndicator() {

        val apiModel = TestNetApiModel()
        assert(ORNetIndicatorClient.indicators.count() == 0)
        ORNetIndicatorClient.show(apiModel)
        ORNetIndicatorClient.hide(apiModel)

        val appContext: Activity = mock()

        ORNetIndicatorClient.add(apiModel, null, appContext, null)
        ORNetIndicatorClient.show(apiModel)
        ORNetIndicatorClient.hide(apiModel)
        ORNetIndicatorClient.remove(apiModel)
    }
}