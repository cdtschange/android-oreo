package com.cdts.demo.dagger.view

import com.cdts.demo.dagger.application.ApplicationComponent
import com.cdts.demo.dagger.view.module.FragmentModule
import dagger.Component


@ActivityScope
@Component(modules = [FragmentModule::class], dependencies = [ApplicationComponent::class])
interface FragmentComponent {
//    fun inject(uiFragment: UIFragment)
//    fun inject(uiFragment: DataFragment)
//    fun inject(fragment: DemoFragment)
//    fun inject(fragment: DemoListFragment)
//    fun inject(fragment: DemoWebViewFragment)
//    fun inject(fragment: WebViewListFragment)
//
//    fun inject(fragment: LocationFragment)
//    fun inject(fragment: DeviceInfoFragment)
//    fun inject(fragment: PermissionFragment)
//    fun inject(fragment: CacheFragment)
//    fun inject(fragment: NetworkFragment)
//    fun inject(fragment: JetpackFragment)
//    fun inject(fragment: InstalledAppFragment)
}