package com.cdts.demo.libs

import android.app.Application
import com.didichuxing.doraemonkit.DoraemonKit


object DoraemonLib {
    fun setup(app: Application) {
        DoraemonKit.install(app)
    }
}