package com.cdts.demo.dagger.application.module

import android.app.Application
import com.cdts.demo.dagger.application.ApplicationScope
import com.cdts.demo.ui.application.MyApplication
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