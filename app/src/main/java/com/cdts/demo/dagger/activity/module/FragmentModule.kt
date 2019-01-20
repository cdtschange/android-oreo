package com.cdts.demo.dagger.activity.module

import android.support.v4.app.Fragment
import com.cdts.demo.dagger.activity.ActivityScope
import com.cdts.demo.ui.tab.repository.MenuListRepository
import com.cdts.demo.ui.tab.viewmodel.MenuListViewModel
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
//
//    @Provides
//    @ActivityScope
//    internal fun provideDataRepository(): DataRepository {
//        return DataRepository()
//    }
//
//    @Provides
//    @ActivityScope
//    internal fun provideWebViewListRepository(): WebViewListRepository {
//        return WebViewListRepository()
//    }
//
//    @Provides
//    @ActivityScope
//    internal fun provideLocationRepository(): LocationRepository {
//        return LocationRepository()
//    }
//    @Provides
//    @ActivityScope
//    internal fun provideDeviceInfoRepository(): DeviceInfoRepository {
//        return DeviceInfoRepository()
//    }
//    @Provides
//    @ActivityScope
//    internal fun providePermissionRepository(): PermissionRepository {
//        return PermissionRepository()
//    }
//    @Provides
//    @ActivityScope
//    internal fun provideCacheRepository(): CacheRepository {
//        return CacheRepository()
//    }
//    @Provides
//    @ActivityScope
//    internal fun provideNetworkRepository(): NetworkRepository {
//        return NetworkRepository()
//    }
//    @Provides
//    @ActivityScope
//    internal fun provideJetpackRepository(): JetpackRepository {
//        return JetpackRepository()
//    }
//    @Provides
//    @ActivityScope
//    internal fun provideInstalledAppRepository(): InstalledAppRepository {
//        return InstalledAppRepository()
//    }
//
//
//
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideDataViewModel(repository: DataRepository): DataViewModel {
//        return AKViewModel.createViewModel(fragment, repository, { DataViewModel(it) })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideWebViewListViewModel(repository: WebViewListRepository): WebViewListViewModel {
//        return AKViewModel.createViewModel(fragment, repository, { WebViewListViewModel(it) })
//    }
//
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideLocationViewModel(repository: LocationRepository): LocationViewModel {
//        return AKViewModel.createViewModel(fragment, repository, { LocationViewModel(it) })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideDeviceInfoViewModel(repository: DeviceInfoRepository): DeviceInfoViewModel {
//        return AKViewModel.createViewModel(fragment, repository, { DeviceInfoViewModel(it) })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun providePermissionViewModel(repository: PermissionRepository): PermissionViewModel {
//        return AKViewModel.createViewModel(fragment, repository, { PermissionViewModel(it) })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideCacheViewModel(repository: CacheRepository): CacheViewModel {
//        return AKViewModel.createViewModel(fragment, repository, { CacheViewModel(it) })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideNetworkViewModel(repository: NetworkRepository): NetworkViewModel {
//        return AKViewModel.createViewModel(fragment, repository, { NetworkViewModel(it) })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideJetpackViewModel(repository: JetpackRepository): JetpackViewModel {
//        return AKViewModel.createViewModel(fragment, repository, { JetpackViewModel(it) })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideInstalledAppViewModel(repository: InstalledAppRepository): InstalledAppViewModel {
//        return AKViewModel.createViewModel(fragment, repository, { InstalledAppViewModel(it) })
//    }


}