package com.cdts.demo.ui.webview.view

import android.support.v4.app.Fragment
import com.cdts.demo.R
import com.cdts.demo.schema.view.webview.BaseWebViewActivity
import com.cdts.demo.schema.view.webview.BaseWebViewFragment
import com.cdts.oreo.ui.router.ORRouter
import com.cdts.oreo.ui.view.actionsheet.ORPhotoActionSheet
import timber.log.Timber

class WebBridgeViewActivity: BaseWebViewActivity() {

    override var fragment: Fragment = WebBridgeViewFragment()


    override fun setupUI() {
        super.setupUI()
        val html = resources.openRawResource(R.raw.webview_bridge).bufferedReader().use { it.readText() }
        (fragment as WebBridgeViewFragment).let {
            it.refreshHeaderEnable = false
            it.htmlData = html
        }
    }
}

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