package com.cdts.demo.schema.view.webview

import android.support.v4.app.Fragment
import com.cdts.demo.schema.view.BaseSingleFragmentActivity

class BaseWebViewActivity: BaseSingleFragmentActivity() {
    override var fragment: Fragment = BaseWebViewFragment()
}