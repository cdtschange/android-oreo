package com.cdts.demo.ui.application

import android.app.Application
import com.cdts.demo.dagger.application.ApplicationComponent
import com.cdts.demo.dagger.application.DaggerApplicationComponent
import com.cdts.demo.dagger.application.module.ApplicationModule
import com.cdts.demo.libs.DoraemonLib
import com.cdts.demo.libs.UMengLib
import com.cdts.oreo.data.crash.ORCrashHandler
import com.cdts.oreo.data.local.ORDiskCache
import com.cdts.oreo.ui.application.ORApplication
import com.pintec.velo.libs.TimberLib
import timber.log.Timber

class MyApplication: Application() {

    companion object {
        var applicationComponent: ApplicationComponent? = null
            private set
    }
    override fun onCreate() {
        super.onCreate()
        ORApplication.application = this
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()

        UMengLib.setup(this)
        DoraemonLib.setup(this)
        TimberLib.setup()

        ORCrashHandler.shared.registerExceptionHandler { thread, ex, message ->
            Timber.e(message)
        }
    }
}