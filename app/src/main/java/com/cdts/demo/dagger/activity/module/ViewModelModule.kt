package com.cdts.demo.dagger.activity.module

import android.arch.lifecycle.ViewModel
import com.cdts.demo.dagger.activity.ActivityScope
import com.cdts.demo.data.cache.repository.CacheRepository
import com.cdts.demo.data.device.repository.DeviceInfoRepository
import com.cdts.demo.data.location.repository.LocationRepository
import com.cdts.demo.data.network.repository.NetworkRepository
import com.cdts.demo.tab.repository.MenuListRepository
import com.cdts.demo.ui.indicatorview.repository.IndicatorViewRepository
import com.cdts.demo.ui.listview.repository.ListTypeRepository
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

    @Provides
    @ActivityScope
    internal fun provideListTypeRepository(): ListTypeRepository {
        return ListTypeRepository()
    }

    @Provides
    @ActivityScope
    internal fun provideIndicatorViewRepository(): IndicatorViewRepository {
        return IndicatorViewRepository()
    }

    @Provides
    @ActivityScope
    internal fun provideCacheRepository(): CacheRepository {
        return CacheRepository()
    }

    @Provides
    @ActivityScope
    internal fun provideNetworkRepository(): NetworkRepository {
        return NetworkRepository()
    }

    @Provides
    @ActivityScope
    internal fun provideDeviceInfoRepository(): DeviceInfoRepository {
        return DeviceInfoRepository()
    }

    @Provides
    @ActivityScope
    internal fun provideLocationRepository(): LocationRepository {
        return LocationRepository()
    }


}