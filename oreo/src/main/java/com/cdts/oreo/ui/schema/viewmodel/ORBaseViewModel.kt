package com.cdts.oreo.ui.schema.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cdts.oreo.ui.schema.repository.ORBaseRepository
import io.reactivex.Observable

open class ORBaseViewModel: ViewModel() {

    companion object {
        @Suppress("UNCHECKED_CAST")
        inline fun<reified V: ViewModel> createViewModel(activity: FragmentActivity, crossinline factory: () -> V): V {
            return ViewModelProviders.of(activity, object: ViewModelProvider.Factory {
                override fun <T: ViewModel?> create(modelClass: Class<T>): T {
                    return factory() as T
                }
            }).get(V::class.java)
        }
        @Suppress("UNCHECKED_CAST")
        inline fun<reified V: ViewModel> createViewModel(fragment: Fragment, crossinline factory: () -> V): V {
            return ViewModelProviders.of(fragment, object: ViewModelProvider.Factory {
                override fun <T: ViewModel?> create(modelClass: Class<T>): T {
                    return factory() as T
                }
            }).get(V::class.java)
        }
    }

    init {
        setupDagger()
    }

    open lateinit var repository: ORBaseRepository

    open fun setupDagger() {}

    open fun fetchData(): Observable<Any> {
        return Observable.just(true)
    }
}