package com.cdts.oreo.ui.application

import android.app.Application
import com.cdts.oreo.dagger.application.ApplicationComponent
import com.cdts.oreo.dagger.application.module.ApplicationModule

class MyApplication: Application() {

    companion object {
        var applicationComponent: ApplicationComponent? = null
            private set
    }
    override fun onCreate() {
        super.onCreate()
        ORApplication.application = this
//        applicationComponent = DaggerApplicationComponent
//            .builder()
//            .applicationModule(ApplicationModule(this))
//            .build()
    }
}