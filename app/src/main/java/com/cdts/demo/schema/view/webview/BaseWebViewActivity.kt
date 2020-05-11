package com.cdts.demo.schema.view.webview

import androidx.fragment.app.Fragment
import com.cdts.demo.schema.view.BaseSingleFragmentActivity

open class BaseWebViewActivity: BaseSingleFragmentActivity() {

    var url: String? = null

    override var fragment: Fragment = BaseWebViewFragment()

    override fun setupUI() {
        super.setupUI()
        (fragment as BaseWebViewFragment).let {
            it.url = url
        }
    }
}