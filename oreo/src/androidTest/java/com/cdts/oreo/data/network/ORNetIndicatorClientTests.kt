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

//    func testRemoveIndicator() {
//        let expectation = XCTestExpectation(description: "Complete")
//
//        let api = TestNetApi()
//        api.baseUrlString = Constant.urlString
//        api.baseHeaders = Constant.baseHeaders
//        api.headers = Constant.headers
//        api.baseParams = Constant.baseParams
//        api.url = "get"
//        api.params = Constant.params
//        let handler = TestRequestHandler()
//        handler.testApi = api
//        api.requestHandler = handler
//        let indicator = APIndicator()
//        let view = UIView()
//        let text = "Loading"
//        api.setIndicator(indicator, view: view, text: text).signal(format: .json).on(started: {
//            APNetIndicatorClient.remove(indicator: indicator)
//        }, failed: { error in
//                assertionFailure()
//            expectation.fulfill()
//        }, completed: {
//            assertionFailure()
//            expectation.fulfill()
//        }, interrupted: {
//            let model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//            assert(model == nil)
//            expectation.fulfill()
//        }, value: { data in
//        }).start()
//
//        wait(for: [expectation], timeout: 10)
//    }

//    func testCancelTask() {
//        let expectation = XCTestExpectation(description: "Complete")
//
//        let api = TestNetApi()
//        api.baseUrlString = Constant.urlString
//        api.baseHeaders = Constant.baseHeaders
//        api.headers = Constant.headers
//        api.baseParams = Constant.baseParams
//        api.url = "get"
//        api.params = Constant.params
//        let handler = TestRequestHandler()
//        handler.testApi = api
//        api.requestHandler = handler
//        let indicator = APIndicator()
//        let view = UIView()
//        let text = "Loading"
//        api.setIndicator(indicator, view: view, text: text).signal(format: .json).on(started: {
//            let model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//            assert(model != nil)
//            assert(model!.indicator!.showing == true)
//            model!.task!.cancel()
//        }, failed: { error in
//                assertionFailure()
//            expectation.fulfill()
//        }, completed: {
//            assertionFailure()
//            expectation.fulfill()
//        }, interrupted: {
//            let model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//            assert(model == nil)
//            assert(indicator.showing == false)
//            expectation.fulfill()
//        }, value: { data in
//        }).start()
//
//        wait(for: [expectation], timeout: 10)
//    }
//
//    func testMockCancel() {
//        let api = TestNetApi()
//        api.baseUrlString = Constant.urlString
//        api.baseHeaders = Constant.baseHeaders
//        api.headers = Constant.headers
//        api.baseParams = Constant.baseParams
//        api.url = "get"
//        api.params = Constant.params
//        let handler = TestRequestHandler()
//        handler.testApi = api
//        api.requestHandler = handler
//        let indicator = APIndicator()
//        let view = UIView()
//        let text = "Loading"
//
//        _ = api.setIndicator(indicator, view: view, text: text)
//        let task = URLSessionTask()
//        APNetIndicatorClient.bind(api: api, task: task)
//        var model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//        assert(model != nil)
//        NotificationCenter.default.post(name: Notification.Name.Task.DidResume, object: nil, userInfo: [Notification.Key.Task: task])
//        model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//        assert(model != nil)
//        assert(model?.indicator?.showing == true)
//        NotificationCenter.default.post(name: Notification.Name.Task.DidCancel, object: nil, userInfo: [Notification.Key.Task: task])
//        model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//        assert(model == nil)
//
//    }
//
//    func testMockSuspend() {
//
//        let api = TestNetApi()
//        api.baseUrlString = Constant.urlString
//        api.baseHeaders = Constant.baseHeaders
//        api.headers = Constant.headers
//        api.baseParams = Constant.baseParams
//        api.url = "get"
//        api.params = Constant.params
//        let handler = TestRequestHandler()
//        handler.testApi = api
//        api.requestHandler = handler
//        let indicator = APIndicator()
//        let view = UIView()
//        let text = "Loading"
//
//        _ = api.setIndicator(indicator, view: view, text: text)
//        let task = URLSessionTask()
//        APNetIndicatorClient.bind(api: api, task: task)
//        var model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//        assert(model != nil)
//        NotificationCenter.default.post(name: Notification.Name.Task.DidResume, object: nil, userInfo: [Notification.Key.Task: task])
//        model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//        assert(model != nil)
//        assert(model?.indicator?.showing == true)
//        NotificationCenter.default.post(name: Notification.Name.Task.DidSuspend, object: nil, userInfo: [Notification.Key.Task: task])
//        model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//        assert(model != nil)
//        assert(model?.indicator?.showing == false)
//        NotificationCenter.default.post(name: Notification.Name.Task.DidResume, object: nil, userInfo: [Notification.Key.Task: task])
//        model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//        assert(model != nil)
//        assert(model?.indicator?.showing == true)
//        NotificationCenter.default.post(name: Notification.Name.Task.DidComplete, object: nil, userInfo: [Notification.Key.Task: task])
//        model = APNetIndicatorClient.getIndicatorModel(identifier: api.identifier)
//        assert(model == nil)
//    }

}