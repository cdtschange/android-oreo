package com.cdts.demo.schema.view.webview

import android.view.View
import androidx.databinding.ViewDataBinding
import com.cdts.demo.R
import com.cdts.demo.dagger.activity.DaggerFragmentComponent
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.ui.schema.view.Bind
import com.cdts.oreo.ui.schema.view.webview.ORBaseWebViewFragment
import com.cdts.oreo.ui.view.actionsheet.ORPhotoActionSheet
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import com.github.lzyzsd.jsbridge.BridgeWebView
import kotlinx.android.synthetic.main.fragment_webview.*
import kotlinx.android.synthetic.main.toolbar_webview_bottom.*
import timber.log.Timber
import javax.inject.Inject

open class BaseWebViewFragment: ORBaseWebViewFragment() {
    override val layoutResID: Int = R.layout.fragment_webview

    override val titleBar: ORToolBar?
        get() = demoToolBar

    @Inject
    override lateinit var indicator: ORIndicatorProtocol
    override var webView: BridgeWebView by Bind(R.id.webView)
    override var progressBarColor: Int = R.color.colorPrimary
    override var webViewTitle: String? = null
        set(value) {
            field = value
            demoToolBar?.centerText = value ?: ""
        }

    override fun setupDagger() {
        super.setupDagger()
        val fragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(MyApplication.applicationComponent)
                .fragmentModule(FragmentModule(this))
                .build()

        fragmentComponent.inject(this)
    }

    override fun setupBinding(binding: ViewDataBinding?) {
        super.setupBinding(binding)
        toolbarBottomBack.setOnClickListener {
            webView.goBack()
        }
        toolbarBottomForward.setOnClickListener {
            webView.goForward()
        }
    }

    override fun setupNavigation() {
        super.setupNavigation()

        demoToolBar?.centerText = webViewTitle ?: ""
    }

    override fun settingWebView() {
        super.settingWebView()
        registerWebViewMethod("webInvokeNative") { _, function ->
            function.onCallBack("Native: Callback")
        }
        registerWebViewMethod("choosePhoto") { _, function ->
            ORPhotoActionSheet.showPhoto(activity!!) { success, uri, error ->
                if (success) {
                    Timber.i("$success $uri")
                    function.onCallBack(uri?.toString() ?: "")
                } else {
                    Timber.e("$success ${error?.toString()}")
                    function.onCallBack(error?.toString() ?: "")
                }
            }
        }
    }

    override fun webViewRefreshData() {
        super.webViewRefreshData()
        if (webView.canGoBack()) {
            if (toolbarBottom.visibility == View.GONE) {
                toolbarBottom.visibility = View.VISIBLE
            }
        }
        toolbarBottomBack.isEnabled = webView.canGoBack()
        toolbarBottomBack.alpha = if (webView.canGoBack()) 1.0f else 0.5f
        toolbarBottomForward.isEnabled = webView.canGoForward()
        toolbarBottomForward.alpha = if (webView.canGoForward()) 1.0f else 0.5f
    }



}