package com.cdts.demo.ui.webview.view

import com.cdts.demo.schema.view.webview.BaseWebViewFragment
import com.cdts.oreo.ui.router.ORRouter
import com.cdts.oreo.ui.view.actionsheet.ORPhotoActionSheet
import timber.log.Timber


class WebBridgeViewFragment: BaseWebViewFragment() {
    override fun settingWebView() {
        super.settingWebView()

        registerWebViewMethod("jsCallNative") { _, callback ->
            callback.onCallBack("Response from native.")
        }
        registerWebViewMethod("choosePhoto") { _, callback ->
            ORPhotoActionSheet.showPhoto(ORRouter.topActivity()) { success, uri, error ->
                if (success) {
                    callback.onCallBack(uri.toString())
                } else {
                    Timber.e(error)
                }
            }
        }
    }
}