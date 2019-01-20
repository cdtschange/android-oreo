package com.cdts.demo.ui.tab.viewmodel

import com.cdts.demo.dagger.activity.DaggerViewModelComponent
import com.cdts.demo.dagger.activity.module.ViewModelModule
import com.cdts.demo.schema.viewmodel.BaseListViewModel
import com.cdts.demo.ui.application.MyApplication
import com.cdts.demo.ui.tab.repository.MenuListRepository
import com.cdts.demo.ui.tab.repository.MenuType
import com.cdts.oreo.ui.schema.repository.ORBaseRepository
import io.reactivex.Observable
import javax.inject.Inject


class MenuListViewModel: BaseListViewModel() {

    var type: MenuType = MenuType.None

    @Inject
    lateinit var mRepository: MenuListRepository
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
        return Observable.just(mRepository.fetchMenuItems(type)).map { data ->
            updateData(data)
            data
        }
    }
}