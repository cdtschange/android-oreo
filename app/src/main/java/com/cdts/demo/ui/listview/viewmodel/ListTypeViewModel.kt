package com.cdts.demo.ui.listview.viewmodel

import com.cdts.demo.dagger.activity.DaggerViewModelComponent
import com.cdts.demo.dagger.activity.module.ViewModelModule
import com.cdts.demo.schema.viewmodel.BaseListViewModel
import com.cdts.demo.ui.application.MyApplication
import com.cdts.demo.ui.listview.repository.ListTypeRepository
import com.cdts.oreo.ui.schema.repository.ORBaseRepository
import io.reactivex.Observable
import javax.inject.Inject

class ListTypeViewModel: BaseListViewModel() {
    @Inject
    lateinit var mRepository: ListTypeRepository
    override var repository: ORBaseRepository = mRepository

    override fun setupDagger() {
        super.setupDagger()
        val viewModelComponent = DaggerViewModelComponent.builder()
            .applicationComponent(MyApplication.applicationComponent)
            .viewModelModule(ViewModelModule(this))
            .build()

        viewModelComponent.inject(this)
    }

    override fun fetchData(): Observable<Any> {
        return mRepository.fetchData(dataIndex * listLoadNumber, listLoadNumber).map { data ->
            appendDataArray(data)
            data
        }
    }
}