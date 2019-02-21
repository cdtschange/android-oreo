package com.cdts.demo.dagger.activity.module

import android.support.v4.app.Fragment
import com.cdts.demo.dagger.activity.ActivityScope
import com.cdts.demo.data.cache.viewmodel.CacheViewModel
import com.cdts.demo.data.device.viewmodel.DeviceInfoViewModel
import com.cdts.demo.data.location.viewmodel.LocationViewModel
import com.cdts.demo.data.network.viewmodel.NetworkViewModel
import com.cdts.demo.tab.viewmodel.MenuListViewModel
import com.cdts.demo.ui.indicatorview.viewmodel.IndicatorViewModel
import com.cdts.demo.ui.listview.viewmodel.ListTypeViewModel
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import dagger.Module
import dagger.Provides


@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    @ActivityScope
    internal fun provideFragment(): Fragment {
        return fragment
    }

    @Provides
    @ActivityScope
    internal fun provideIndicator(): ORIndicatorProtocol {
        return ORIndicator()
    }

    @Provides
    @ActivityScope
    @Suppress("UNCHECKED_CAST")
    internal fun provideMenuListViewModel(): MenuListViewModel {
        return ORBaseViewModel.createViewModel(fragment) { MenuListViewModel() }
    }

    @Provides
    @ActivityScope
    @Suppress("UNCHECKED_CAST")
    internal fun provideListTypeViewModel(): ListTypeViewModel {
        return ORBaseViewModel.createViewModel(fragment) { ListTypeViewModel() }
    }

    @Provides
    @ActivityScope
    @Suppress("UNCHECKED_CAST")
    internal fun provideIndicatorViewModel(): IndicatorViewModel {
        return ORBaseViewModel.createViewModel(fragment) { IndicatorViewModel() }
    }

    @Provides
    @ActivityScope
    @Suppress("UNCHECKED_CAST")
    internal fun provideCacheViewModel(): CacheViewModel {
        return ORBaseViewModel.createViewModel(fragment) { CacheViewModel() }
    }

    @Provides
    @ActivityScope
    @Suppress("UNCHECKED_CAST")
    internal fun provideNetworkViewModel(): NetworkViewModel {
        return ORBaseViewModel.createViewModel(fragment) { NetworkViewModel() }
    }

    @Provides
    @ActivityScope
    @Suppress("UNCHECKED_CAST")
    internal fun provideDeviceInfoViewModel(): DeviceInfoViewModel {
        return ORBaseViewModel.createViewModel(fragment) { DeviceInfoViewModel() }
    }

    @Provides
    @ActivityScope
    @Suppress("UNCHECKED_CAST")
    internal fun provideLocationViewModel(): LocationViewModel {
        return ORBaseViewModel.createViewModel(fragment) { LocationViewModel() }
    }

}