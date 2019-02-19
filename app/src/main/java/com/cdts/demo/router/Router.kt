package com.cdts.demo.router

import com.cdts.demo.schema.view.webview.BaseWebViewActivity
import com.cdts.oreo.ui.router.ORRouter

fun ORRouter.routeToUrl(url: String, params: Map<String, Any> = mapOf()): Boolean {
    return ORRouter.routeToUrl(BaseWebViewActivity::class.java.name, url, params)
}