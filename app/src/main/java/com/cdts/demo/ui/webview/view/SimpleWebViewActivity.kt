package com.cdts.demo.ui.webview.view

import androidx.fragment.app.Fragment
import com.cdts.demo.R
import com.cdts.demo.schema.view.webview.BaseWebViewActivity
import com.cdts.demo.schema.view.webview.BaseWebViewFragment

class SimpleWebViewActivity: BaseWebViewActivity() {

    override var fragment: Fragment = BaseWebViewFragment()


    override fun setupUI() {
        super.setupUI()
        val html = resources.openRawResource(R.raw.webview_simple).bufferedReader().use { it.readText() }
        (fragment as BaseWebViewFragment).let {
            it.refreshHeaderEnable = false
            it.htmlData = html
        }
    }
}