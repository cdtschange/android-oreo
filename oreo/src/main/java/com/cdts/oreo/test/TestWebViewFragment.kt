package com.cdts.oreo.test

import com.cdts.oreo.R
import com.cdts.oreo.ui.schema.view.Bind
import com.cdts.oreo.ui.schema.view.webview.ORBaseWebViewFragment
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import com.github.lzyzsd.jsbridge.BridgeWebView
import kotlinx.android.synthetic.main.test_fragment_empty.*
import javax.inject.Inject

class TestWebViewFragment: ORBaseWebViewFragment() {
    override var layoutResID: Int = R.layout.test_fragment_webview
    override var indicator: ORIndicatorProtocol = ORIndicator()
    override var viewModel: ORBaseViewModel = ORBaseViewModel()

    override var titleBar: ORToolBar? = null
        get() = testToolBar

    override var webView: BridgeWebView by Bind(R.id.webView)
    override var progressBarColor: Int = R.color.colorPrimary
    override var webViewTitle: String? = null


}