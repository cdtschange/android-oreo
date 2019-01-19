package com.cdts.demo.schema.viewmodel

import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import javax.inject.Inject

open class BaseViewModel: ORBaseViewModel() {

    init {
//        val viewModelComponent = DaggerViewModelComponent.builder()
//                .applicationComponent(MyApplication.applicationComponent)
//                .viewModelModule(ViewModelModule(this))
//                .build()
//
//        viewModelComponent.inject(this)
    }

}