package com.cdts.demo.dagger.activity

import com.cdts.demo.dagger.application.ApplicationComponent
import com.cdts.demo.dagger.activity.module.ActivityModule
import com.cdts.demo.schema.view.BaseActivity
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [ApplicationComponent::class])
interface ActivityComponent {
    fun inject(activity: BaseActivity)
}

