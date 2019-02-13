package com.cdts.oreo.test

import android.app.Application
import com.cdts.oreo.ui.application.ORApplication

class TestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ORApplication.application = this
    }
}