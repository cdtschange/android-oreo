package com.cdts.oreo.ui.application

import android.app.Application

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ORApplication.application = this
    }
}