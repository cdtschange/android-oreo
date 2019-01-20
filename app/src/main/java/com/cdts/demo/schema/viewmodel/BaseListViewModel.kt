package com.cdts.demo.schema.viewmodel

import com.cdts.demo.dagger.activity.DaggerViewModelComponent
import com.cdts.demo.dagger.activity.module.ViewModelModule
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.ui.schema.viewmodel.ORBaseListViewModel

open class BaseListViewModel: ORBaseListViewModel() {

    override fun setupDagger() {
        super.setupDagger()

        val viewModelComponent = DaggerViewModelComponent.builder()
            .applicationComponent(MyApplication.applicationComponent)
            .viewModelModule(ViewModelModule(this))
            .build()

        viewModelComponent.inject(this)
    }
}