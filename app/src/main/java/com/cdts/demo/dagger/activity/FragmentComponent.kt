package com.cdts.demo.dagger.activity

import com.cdts.demo.dagger.application.ApplicationComponent
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.data.cache.view.CacheFragment
import com.cdts.demo.schema.view.BaseFragment
import com.cdts.demo.schema.view.BaseListFragment
import com.cdts.demo.schema.view.webview.BaseWebViewFragment
import com.cdts.demo.tab.view.DataFragment
import com.cdts.demo.tab.view.MenuListViewFragment
import com.cdts.demo.tab.view.UIFragment
import com.cdts.demo.ui.indicatorview.view.IndicatorViewFragment
import com.cdts.demo.ui.listview.view.ListTypeFragment
import dagger.Component


@ActivityScope
@Component(modules = [FragmentModule::class], dependencies = [ApplicationComponent::class])
interface FragmentComponent {
    fun inject(fragment: BaseFragment)
    fun inject(fragment: BaseListFragment)
    fun inject(fragment: BaseWebViewFragment)

    fun inject(fragment: MenuListViewFragment)
    fun inject(fragment: UIFragment)
    fun inject(fragment: DataFragment)
    fun inject(fragment: ListTypeFragment)
    fun inject(fragment: IndicatorViewFragment)
    fun inject(fragment: CacheFragment)
}