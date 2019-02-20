package com.cdts.demo.ui.webview.view

import android.support.v4.app.Fragment
import com.cdts.demo.R
import com.cdts.demo.schema.view.webview.BaseWebViewActivity

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