package com.cdts.demo.dagger.activity.module

import androidx.appcompat.app.AppCompatActivity
import com.cdts.demo.dagger.activity.ActivityScope
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityScope
    internal fun provideActivity(): AppCompatActivity {
        return activity
    }

    @Provides
    @ActivityScope
    internal fun provideIndicator(): ORIndicatorProtocol {
        return ORIndicator()
    }
}