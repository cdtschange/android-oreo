package com.cdts.demo.dagger.activity

import com.cdts.demo.dagger.activity.module.ViewModelModule
import com.cdts.demo.dagger.application.ApplicationComponent
import com.cdts.demo.data.cache.viewmodel.CacheViewModel
import com.cdts.demo.data.device.viewmodel.DeviceInfoViewModel
import com.cdts.demo.data.network.viewmodel.NetworkViewModel
import com.cdts.demo.schema.viewmodel.BaseListViewModel
import com.cdts.demo.schema.viewmodel.BaseViewModel
import com.cdts.demo.tab.viewmodel.MenuListViewModel
import com.cdts.demo.ui.indicatorview.viewmodel.IndicatorViewModel
import com.cdts.demo.ui.listview.viewmodel.ListTypeViewModel
import dagger.Component


@ActivityScope
@Component(modules = [ViewModelModule::class], dependencies = [ApplicationComponent::class])
interface ViewModelComponent {
    fun inject(viewModel: BaseViewModel)
    fun inject(viewModel: BaseListViewModel)

    fun inject(viewModel: MenuListViewModel)
    fun inject(viewModel: ListTypeViewModel)
    fun inject(viewModel: IndicatorViewModel)
    fun inject(viewModel: CacheViewModel)
    fun inject(viewModel: NetworkViewModel)
    fun inject(viewModel: DeviceInfoViewModel)
}