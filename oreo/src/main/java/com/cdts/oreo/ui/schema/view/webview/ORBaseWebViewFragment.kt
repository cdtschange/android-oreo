package com.cdts.oreo.ui.schema.view.webview

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebView
import android.webkit.WebViewClient
import com.cdts.oreo.ui.schema.view.ORBaseFragment


abstract class ORBaseWebViewFragment: ORBaseFragment(), ORWebKit {

    @JvmField var url: String? = null
    @JvmField var htmlData: String? = null

    override var webViewClient: WebViewClient? = null
    override var progressBarEnable: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    override fun setupBinding(binding: ViewDataBinding?) {
        super.setupBinding(binding)
        settingWebView()
    }

    override fun loadData() {
        super.loadData()
        url?.let {
            requestUrl(it)
            return
        }
        htmlData?.let {
            requestHtmlData(it)
            return
        }
    }

    override fun webViewWillLoad(view: WebView?, url: String?): Boolean {
        indicator.show(activity, null)
        return super.webViewWillLoad(view, url)
    }
    override fun webViewDidLoadSuccess(view: WebView?, url: String?) {
        indicator.hide()
        super.webViewDidLoadSuccess(view, url)
    }
    override fun webViewDidLoadError(view: WebView?, error: WebResourceError?) {
        indicator.hide()
        super.webViewDidLoadError(view, error)
    }
}