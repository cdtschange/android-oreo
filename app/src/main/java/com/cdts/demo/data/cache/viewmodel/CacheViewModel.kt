package com.cdts.demo.data.cache.viewmodel

import com.cdts.demo.dagger.activity.DaggerViewModelComponent
import com.cdts.demo.dagger.activity.module.ViewModelModule
import com.cdts.demo.data.cache.repository.CacheModel
import com.cdts.demo.data.cache.repository.CacheRepository
import com.cdts.demo.data.cache.repository.CacheType
import com.cdts.demo.schema.viewmodel.BaseListViewModel
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.ui.schema.repository.ORBaseRepository
import io.reactivex.Observable
import javax.inject.Inject

class CacheViewModel: BaseListViewModel() {
    @Inject
    lateinit var mRepository: CacheRepository
    override var repository: ORBaseRepository = mRepository

    var type: CacheType = CacheType.Disk

    override fun setupDagger() {
        super.setupDagger()
        val viewModelComponent = DaggerViewModelComponent.builder()
            .applicationComponent(MyApplication.applicationComponent)
            .viewModelModule(ViewModelModule(this))
            .build()

        viewModelComponent.inject(this)
    }

    override fun fetchData(): Observable<Any> {
        val data = arrayListOf<CacheModel>()
        when(type) {
            CacheType.Cache -> {
                data.add(CacheModel("String", mRepository.cacheString))
                data.add(CacheModel("Int", mRepository.cacheInt))
                data.add(CacheModel("Double", mRepository.cacheDouble))
                data.add(CacheModel("Boolean", mRepository.cacheBoolean))
            }
            CacheType.Disk -> {
                data.add(CacheModel("String", mRepository.diskString))
                data.add(CacheModel("Int", mRepository.diskInt))
                data.add(CacheModel("Double", mRepository.diskDouble))
                data.add(CacheModel("Boolean", mRepository.diskBoolean))
            }
            CacheType.SharedPreferences -> {
                data.add(CacheModel("String", mRepository.sharedPreferencesString))
                data.add(CacheModel("Int", mRepository.sharedPreferencesInt))
                data.add(CacheModel("Double", mRepository.sharedPreferencesDouble))
                data.add(CacheModel("Boolean", mRepository.sharedPreferencesBoolean))
            }
            CacheType.LiveData -> {
                data.add(CacheModel("String", mRepository.liveDataString.value))
                data.add(CacheModel("Int", mRepository.liveDataInt.value))
                data.add(CacheModel("Double", mRepository.liveDataDouble.value))
                data.add(CacheModel("Boolean", mRepository.liveDataBoolean.value))
            }
        }
        return Observable.just(data).map { data ->
            appendDataArray(data)
            data
        }
    }

    fun updateData(type: CacheType, key: String): CacheModel? {
        return when(type) {
            CacheType.Cache -> {
                when(key) {
                    "String" -> CacheModel("String", mRepository.cacheString)
                    "Int" -> CacheModel("Int", mRepository.cacheInt)
                    "Double" -> CacheModel("Double", mRepository.cacheDouble)
                    "Boolean" -> CacheModel("Boolean", mRepository.cacheBoolean)
                    else -> null
                }
            }
            CacheType.Disk -> {
                when(key) {
                    "String" -> CacheModel("String", mRepository.diskString)
                    "Int" -> CacheModel("Int", mRepository.diskInt)
                    "Double" -> CacheModel("Double", mRepository.diskDouble)
                    "Boolean" -> CacheModel("Boolean", mRepository.diskBoolean)
                    else -> null
                }
            }
            CacheType.SharedPreferences -> {
                when(key) {
                    "String" -> CacheModel("String", mRepository.sharedPreferencesString)
                    "Int" -> CacheModel("Int", mRepository.sharedPreferencesInt)
                    "Double" -> CacheModel("Double", mRepository.sharedPreferencesDouble)
                    "Boolean" -> CacheModel("Boolean", mRepository.sharedPreferencesBoolean)
                    else -> null
                }
            }
            CacheType.LiveData -> {
                when(key) {
                    "String" -> CacheModel("String", mRepository.liveDataString.value)
                    "Int" -> CacheModel("Int", mRepository.liveDataInt.value)
                    "Double" -> CacheModel("Double", mRepository.liveDataDouble.value)
                    "Boolean" -> CacheModel("Boolean", mRepository.liveDataBoolean.value)
                    else -> null
                }
            }
        }
    }

    private fun fetchString(type: CacheType): String? {
        return when(type) {
            CacheType.Cache -> mRepository.cacheString
            CacheType.Disk -> mRepository.diskString
            CacheType.SharedPreferences -> mRepository.sharedPreferencesString
            CacheType.LiveData -> mRepository.liveDataString.value
        }
    }
    private fun fetchInt(type: CacheType): Int? {
        return when(type) {
            CacheType.Cache -> mRepository.cacheInt
            CacheType.Disk -> mRepository.diskInt
            CacheType.SharedPreferences -> mRepository.sharedPreferencesInt
            CacheType.LiveData -> mRepository.liveDataInt.value
        }
    }
    private fun fetchDouble(type: CacheType): Double? {
        return when(type) {
            CacheType.Cache -> mRepository.cacheDouble
            CacheType.Disk -> mRepository.diskDouble
            CacheType.SharedPreferences -> mRepository.sharedPreferencesDouble
            CacheType.LiveData -> mRepository.liveDataDouble.value
        }
    }
    private fun fetchBoolean(type: CacheType): Boolean? {
        return when(type) {
            CacheType.Cache -> mRepository.cacheBoolean
            CacheType.Disk -> mRepository.diskBoolean
            CacheType.SharedPreferences -> mRepository.sharedPreferencesBoolean
            CacheType.LiveData -> mRepository.liveDataBoolean.value
        }
    }

    fun saveString(type: CacheType) {
        var value = fetchString(type)
        value = if (value == null || value == "Z") {
            "A"
        } else {
            (value[0].toInt() + 1).toChar().toString()
        }
        when(type) {
            CacheType.Cache -> mRepository.cacheString = value
            CacheType.Disk -> mRepository.diskString = value
            CacheType.SharedPreferences -> mRepository.sharedPreferencesString = value
            CacheType.LiveData -> mRepository.liveDataString.value = value
        }
    }

    fun saveInt(type: CacheType) {
        var value = fetchInt(type)
        value = if (value == null || value == 9) {
            0
        } else {
            value + 1
        }
        when(type) {
            CacheType.Cache -> mRepository.cacheInt = value
            CacheType.Disk -> mRepository.diskInt = value
            CacheType.SharedPreferences -> mRepository.sharedPreferencesInt = value
            CacheType.LiveData -> mRepository.liveDataInt.value = value
        }
    }

    fun saveDouble(type: CacheType) {
        var value = fetchDouble(type)
        value = if (value == null || value >= 1.0) {
            0.0
        } else {
            value + 0.1
        }
        when(type) {
            CacheType.Cache -> mRepository.cacheDouble = value
            CacheType.Disk -> mRepository.diskDouble = value
            CacheType.SharedPreferences -> mRepository.sharedPreferencesDouble = value
            CacheType.LiveData -> mRepository.liveDataDouble.value = value
        }
    }

    fun saveBool(type: CacheType) {
        var value = fetchBoolean(type)
        value = if (value == null) {
            true
        } else {
            !value
        }
        when(type) {
            CacheType.Cache -> mRepository.cacheBoolean = value
            CacheType.Disk -> mRepository.diskBoolean = value
            CacheType.SharedPreferences -> mRepository.sharedPreferencesBoolean = value
            CacheType.LiveData -> mRepository.liveDataBoolean.value = value
        }
    }
}