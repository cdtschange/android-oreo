package com.cdts.demo.dagger.activity

import com.cdts.demo.dagger.application.ApplicationComponent
import com.cdts.demo.dagger.activity.module.ActivityModule
import com.cdts.demo.schema.view.BaseActivity
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [ApplicationComponent::class])
interface ActivityComponent {
    fun inject(activity: BaseActivity)
//    fun inject(activity: DemoWebViewActivity)
//    fun inject(activity: ToolBarActivity)
//    fun inject(activity: HudActivity)
//    fun inject(activity: AlertActivity)
//    fun inject(activity: LifecycleActivity)
//    fun inject(activity: BannerActivity)
//
//    fun inject(activity: DiskCacheActivity)
//    fun inject(activity: LiveDataCacheActivity)
//    fun inject(activity: NetworkGetActivity)
//    fun inject(activity: NetworkPostActivity)
//    fun inject(activity: NetworkPutActivity)
//    fun inject(activity: NetworkDeleteActivity)
//    fun inject(activity: DataBindingActivity)
}

