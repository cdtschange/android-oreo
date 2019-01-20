package com.cdts.demo.dagger.activity

import com.cdts.demo.dagger.application.ApplicationComponent
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.schema.view.BaseFragment
import com.cdts.demo.schema.view.BaseListFragment
import com.cdts.demo.schema.view.webview.BaseWebViewFragment
import com.cdts.demo.ui.tab.view.DataFragment
import com.cdts.demo.ui.tab.view.UIFragment
import dagger.Component


@ActivityScope
@Component(modules = [FragmentModule::class], dependencies = [ApplicationComponent::class])
interface FragmentComponent {
    fun inject(fragment: BaseFragment)
    fun inject(fragment: BaseListFragment)
    fun inject(fragment: BaseWebViewFragment)

    fun inject(fragment: UIFragment)
    fun inject(fragment: DataFragment)
//    fun inject(uiFragment: DataFragment)
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