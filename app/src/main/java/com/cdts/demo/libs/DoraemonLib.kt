package com.cdts.demo.libs

import android.app.Application
import com.cdts.demo.router.routeToUrl
import com.cdts.oreo.ui.router.ORRouter
import com.didichuxing.doraemonkit.DoraemonKit


object DoraemonLib {
    fun setup(app: Application) {
        DoraemonKit.install(app)
        DoraemonKit.setWebDoorCallback { context, url ->
            ORRouter.routeToUrl(url)
        }
    }
}