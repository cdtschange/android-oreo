package com.cdts.demo.dagger.activity.module

import android.arch.lifecycle.ViewModel
import com.cdts.demo.dagger.activity.ActivityScope
import com.cdts.demo.ui.tab.repository.MenuListRepository
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule(private val viewModel: ViewModel) {

    @Provides
    @ActivityScope
    internal fun provideViewModel(): ViewModel {
        return viewModel
    }

    @Provides
    @ActivityScope
    internal fun provideMenuListRepository(): MenuListRepository {
        return MenuListRepository()
    }
}