package com.cdts.demo.data.network.viewmodel

import com.cdts.demo.dagger.activity.DaggerViewModelComponent
import com.cdts.demo.dagger.activity.module.ViewModelModule
import com.cdts.demo.data.network.repository.NetworkModel
import com.cdts.demo.data.network.repository.NetworkRepository
import com.cdts.demo.data.network.repository.NetworkType
import com.cdts.demo.schema.viewmodel.BaseListViewModel
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.extension.toPrettyString
import com.cdts.oreo.ui.schema.repository.ORBaseRepository
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class NetworkViewModel: BaseListViewModel() {
    @Inject
    lateinit var mRepository: NetworkRepository
    override var repository: ORBaseRepository = mRepository

    var type: NetworkType = NetworkType.Get

    override fun setupDagger() {
        super.setupDagger()
        val viewModelComponent = DaggerViewModelComponent.builder()
            .applicationComponent(MyApplication.applicationComponent)
            .viewModelModule(ViewModelModule(this))
            .build()

        viewModelComponent.inject(this)
    }

    override fun fetchData(): Observable<Any> {
        val start = Date().time
        return mRepository.fetchData(type).map { api ->
            val end = Date().time
            val elapsedTime = end - start
            val data = arrayListOf<NetworkModel>()
            (api.result!!["headers"] as? Map<String, String>)?.let { headers ->
                for((key, value) in headers) {
                    data.add(NetworkModel("Header - $key", value))
                }
            }
            data.add(NetworkModel("Body", api.result!!.toPrettyString()))
            data.add(NetworkModel("Elapsed Time", (elapsedTime / 1000.0).toString() + " sec"))
            appendDataArray(data)
            data
        }
    }

}