package com.cdts.oreo.dagger.application

import android.app.Application
import com.cdts.oreo.dagger.application.module.ApplicationModule
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun application(): Application
}