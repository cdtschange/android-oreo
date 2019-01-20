package com.cdts.demo.schema.viewmodel

import com.cdts.demo.dagger.activity.DaggerViewModelComponent
import com.cdts.demo.dagger.activity.module.ViewModelModule
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel

open class BaseViewModel: ORBaseViewModel() {

    init {
        val viewModelComponent = DaggerViewModelComponent.builder()
                .applicationComponent(MyApplication.applicationComponent)
                .viewModelModule(ViewModelModule(this))
                .build()

        viewModelComponent.inject(this)
    }

}