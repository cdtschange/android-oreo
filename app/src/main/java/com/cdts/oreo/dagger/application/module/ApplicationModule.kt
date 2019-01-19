package com.cdts.oreo.dagger.application.module

import android.app.Application
import com.cdts.oreo.dagger.application.ApplicationScope
import com.cdts.oreo.ui.application.MyApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: MyApplication) {

    @Provides
    @ApplicationScope
    internal fun provideDemoApplication(): MyApplication {
        return application
    }
    @Provides
    @ApplicationScope
    internal fun provideApplication(): Application {
        return application
    }
}