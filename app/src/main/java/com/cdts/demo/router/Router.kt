package com.cdts.demo.router

import com.cdts.oreo.ui.router.ORRouter

fun ORRouter.routeToUrl(url: String, params: Map<String, Any> = mapOf()): Boolean {
    return ORRouter.routeToUrl("BaseWebViewActivity", url, params)
}