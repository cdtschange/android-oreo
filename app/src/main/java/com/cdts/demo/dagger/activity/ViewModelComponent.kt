package com.cdts.demo.dagger.activity

import com.cdts.demo.dagger.activity.module.ViewModelModule
import com.cdts.demo.dagger.application.ApplicationComponent
import com.cdts.demo.schema.viewmodel.BaseListViewModel
import com.cdts.demo.schema.viewmodel.BaseViewModel
import com.cdts.demo.ui.tab.viewmodel.MenuListViewModel
import dagger.Component


@ActivityScope
@Component(modules = [ViewModelModule::class], dependencies = [ApplicationComponent::class])
interface ViewModelComponent {
    fun inject(viewModel: BaseViewModel)
    fun inject(viewModel: BaseListViewModel)

    fun inject(viewModel: MenuListViewModel)
}