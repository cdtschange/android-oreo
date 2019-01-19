package com.cdts.demo.dagger.view.module

import android.support.v7.app.AppCompatActivity
import com.cdts.demo.dagger.view.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityScope
    internal fun provideActivity(): AppCompatActivity {
        return activity
    }

//    @Provides
//    @ActivityScope
//    internal fun provideIndicator(hud: Hud): Indicator {
//        return TaskIndicator(hud)
//    }
//
//    @Provides
//    @ActivityScope
//    internal fun provideNetworkRequestRepository(): NetworkRequestRepository {
//        return NetworkRequestRepository()
//    }
//    @Provides
//    @ActivityScope
//    internal fun provideDataBindingRepository(): DataBindingRepository {
//        return DataBindingRepository()
//    }
//
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideToolBarViewModel(): ToolBarViewModel {
//        return AKViewModel.createViewModel(activity, { ToolBarViewModel() })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideHudViewModel(): HudViewModel {
//        return AKViewModel.createViewModel(activity, { HudViewModel() })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideAlertViewModel(): AlertViewModel {
//        return AKViewModel.createViewModel(activity, { AlertViewModel() })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideLifecycleViewModel(): LifecycleViewModel {
//        return AKViewModel.createViewModel(activity, { LifecycleViewModel() })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideBannerViewModel(): BannerViewModel {
//        return AKViewModel.createViewModel(activity, { BannerViewModel() })
//    }
//
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideDiskCacheViewModel(): DiskCacheViewModel {
//        return AKViewModel.createViewModel(activity, { DiskCacheViewModel() })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideLiveDataCacheViewModel(): LiveDataCacheViewModel {
//        return AKViewModel.createViewModel(activity, { LiveDataCacheViewModel() })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideNetworkRequestViewModel(repository: NetworkRequestRepository): NetworkRequestViewModel {
//        return AKViewModel.createViewModel(activity, { NetworkRequestViewModel(repository) })
//    }
//    @Provides
//    @ActivityScope
//    @Suppress("UNCHECKED_CAST")
//    internal fun provideDataBindingViewModel(repository: DataBindingRepository): DataBindingViewModel {
//        return AKViewModel.createViewModel(activity, repository, { DataBindingViewModel(it) })
//    }

}