package com.cdts.oreo.ui.schema.view.webview

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.core.view.children
import com.cdts.oreo.config.ORConfig
import com.cdts.oreo.data.network.cookie.ORCookie
import com.cdts.oreo.extension.typeTokenFromJson
import com.cdts.oreo.ui.router.ORRouter
import com.cdts.oreo.ui.schema.view.ORBaseActivity
import com.cdts.oreo.ui.view.actionsheet.ORPhotoActionSheet
import com.github.lzyzsd.jsbridge.BridgeWebView
import com.github.lzyzsd.jsbridge.BridgeWebViewClient
import com.github.lzyzsd.jsbridge.CallBackFunction
import com.google.gson.Gson
import timber.log.Timber
import java.lang.ref.WeakReference
import java.net.URISyntaxException
import java.util.*
import java.util.regex.Pattern


interface ORWebKit: ORWebViewClientDelegate {

    var webViewTitle: String?
    var webViewClient: WebViewClient?
    var progressBarEnable: Boolean
    var progressBarColor: Int



    @SuppressLint("SetJavaScriptEnabled")
    fun settingWebView() {
        if (webViewClient == null) {
            webViewClient = ORWebViewClient(WeakReference(this))
        }
        if (ORConfig.isRelease) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(false)
            }
        }
        webView.webViewClient = webViewClient
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.allowFileAccessFromFileURLs = false
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.setAppCacheEnabled(true)
        webView.settings.setAppCachePath(webView.context.cacheDir.toString() + "")
        if (progressBarEnable) {
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4)
            val progressView = ProgressView(webView.context, null).also {
                it.layoutParams = layoutParams
                it.setColor(progressBarColor)
            }
            webView.addView(progressView)
        }
        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (progressBarEnable) {
                    val progressView: ProgressView? = webView.children.first { it is ProgressView } as? ProgressView
                    if (newProgress == 100) {
                        progressView?.setProgress(newProgress)
                        progressView?.postDelayed({
                            progressView.visibility = View.GONE
                        }, 100)
                    } else {
                        if (progressView?.visibility == View.GONE) {
                            progressView.visibility = View.VISIBLE
                        }
                        progressView?.setProgress(newProgress)
                    }
                }
            }

            fun openFileChooser(callback: ValueCallback<Uri>, AcceptType: String, capture: String) {
                this.openFileChooser(callback)
            }

            fun openFileChooser(callback: ValueCallback<Uri>, AcceptType: String) {
                this.openFileChooser(callback)
            }

            fun openFileChooser(callback: ValueCallback<Uri>) {
                ORRouter.topActivity().let { activity ->
                    ORPhotoActionSheet.showFile(activity) { success, uri, error ->
                        if (success) {
                            callback.onReceiveValue(uri)
                        } else {
                            Timber.e(error)
                        }
                    }
                }
            }
        }

        registerWebViewMethod("showTip") { data, _ ->
            val map: Map<String, Any> = Gson().typeTokenFromJson(data)
            if (map.containsKey("content")) {
                (ORRouter.topActivity() as? ORBaseActivity)?.showTip(map["content"].toString())
            }
        }

        registerWebViewMethod("routeBack") { _, _ ->
            ORRouter.topActivity().finish()
        }

        registerWebViewMethod("refreshData") { _, _ ->
            webViewRefreshData()
        }

        registerWebViewMethod("goToSomeWhere") { data, function ->
            val map: Map<String, Any> = Gson().typeTokenFromJson(data)
            val name = map["name"].toString()
            val params = map["params"] as Map<String, Any>
            val result = ORRouter.routeToName(name, params)
            function.onCallBack(result.toString())
        }

    }

    @Suppress("DEPRECATION")
    fun setCookie(cookies: Array<String>, url: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val cookieManager = CookieManager.getInstance()
            cookieManager.removeAllCookies(null)
            for (cookie in cookies) {
                cookieManager.setCookie(url, cookie)//cookies是在HttpClient中获得的cookie
            }
            cookieManager.flush()
        } else {
            val cookieSyncManager = CookieSyncManager.createInstance(webView.context)
            cookieSyncManager.startSync()
            val cookieManager = CookieManager.getInstance()
            cookieManager.removeAllCookie()
            cookieManager.removeSessionCookie()
            for (cookie in cookies) {
                cookieManager.setCookie(url, cookie)//cookies是在HttpClient中获得的cookie
            }
            cookieSyncManager.stopSync()
            cookieSyncManager.sync()
        }
    }

    fun requestHeader(): Map<String, String> {
        return HashMap()
    }

    fun requestUrl(url: String) {
        setCookie(ORCookie.cookieArray, url)
        webView.loadUrl(url, requestHeader())
    }

    fun requestHtmlData(htmlData: String) {
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.loadData(htmlData, "text/html; charset=UTF-8", null)
    }

    fun invokeWebViewMethod(name: String, data: String, callback: (String) -> Unit = {}) {
        webView.callHandler(name, data) {
            Timber.i("invoke js: $it")
            try {
                callback(it)
            } catch (e: Error) {
                Timber.e(e)
            }
        }
    }
    fun registerWebViewMethod(name: String, callback: (String, CallBackFunction) -> Unit) {
        webView.registerHandler(name) { data, function ->
            Timber.i("invoke native: $data")
            try {
                callback(data, function)
            } catch (e: Error) {
                Timber.e(e)
            }
        }
    }

    override fun webViewWillLoad(view: WebView?, url: String?): Boolean {
        Timber.i("webViewWillLoad: $url")
        return if(url?.endsWith(".apk") == true){
            shouldRouteOutside(view, url)
        } else {
            true
        }
    }

    override fun webViewDidLoadSuccess(view: WebView?, url: String?) {
        Timber.i("webViewDidLoadSuccess: $url")
        webViewRefreshData()
    }

    override fun webViewDidLoadError(view: WebView?, error: WebResourceError?) {
        Timber.e("webViewDidLoadError: ${view?.url ?: ""}")
    }

    override fun webViewShouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        Timber.i("webViewShouldOverrideUrlLoading: $url")
        if (url?.isNormalUrl() == true) return false
        return shouldRouteOutside(view, url)
    }

    fun webViewRefreshData() {
        if (webView.title?.contains("http") == false && webView.title?.contains("html") == false && webView.title.isNotEmpty()) {
            webViewTitle = webView.title
        }
    }

    private fun shouldRouteOutside(view: WebView?, url: String?): Boolean {
        val intent: Intent?
        try {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
        } catch (e: URISyntaxException) {
            Timber.e(e)
            return false
        }

        intent?.component = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            intent?.selector = null
        }

        if (view?.context?.packageManager?.resolveActivity(intent, 0) == null) {
            val packageName = intent.`package`
            try{
                val newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
                webView.context?.startActivity(newIntent)
            } catch (e: ActivityNotFoundException) {
                Timber.e(e)
                return false
            }
            return true
        }

        try {
            view.context?.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Timber.e(e)
            return false
        }
        return true
    }
}

interface ORWebViewClientDelegate {
    var webView: BridgeWebView
    fun webViewWillLoad(view: WebView?, url: String?): Boolean
    fun webViewDidLoadSuccess(view: WebView?, url: String?)
    fun webViewDidLoadError(view: WebView?, error: WebResourceError?)
    fun webViewShouldOverrideUrlLoading(view: WebView?, url: String?): Boolean
}

open class ORWebViewClient(private val delegate: WeakReference<ORWebViewClientDelegate>): BridgeWebViewClient(delegate.get()!!.webView) {

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        handler?.proceed()
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        if(delegate.get()?.webViewWillLoad(view, url) == true) {
            super.onPageStarted(view, url, favicon)
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        delegate.get()?.webViewDidLoadSuccess(view, url)
        super.onPageFinished(view, url)
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        delegate.get()?.webViewDidLoadError(view, error)
        super.onReceivedError(view, request, error)
    }

    @Suppress("DEPRECATION")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return shouldOverrideUrlLoading(view, request?.url.toString())
    }

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        delegate.get()?.let {
            return it.webViewShouldOverrideUrlLoading(view, url)
        }
        return super.shouldOverrideUrlLoading(view, url)
    }
}

fun String.isNormalUrl(): Boolean {
    val lowerCaseUrl = this.toLowerCase()
    val acceptUrlSchemaPattern = Pattern.compile("(?i)" + '(' + "(?:http|https|ftp|file)://" + "|(?:inline|data|about|javascript):" + "|(?:.*:.*@)" + ')' + "(.*)")
    val acceptedUrlSchemeMatcher = acceptUrlSchemaPattern.matcher(lowerCaseUrl)
    if (acceptedUrlSchemeMatcher.matches()) {
        return true
    }
    return false
}