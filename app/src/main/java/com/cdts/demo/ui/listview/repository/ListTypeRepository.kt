package com.cdts.demo.ui.listview.repository

import com.cdts.demo.schema.repository.BaseRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ListTypeRepository: BaseRepository() {

    fun fetchData(index: Int, size: Int): Observable<List<String>> {
        val data = when(index) {
            0 -> {
                val start = index + 1
                (start..size).map { "This is $it item." }
            }
            else -> {
                val start = index + 1
                val count = start + size / 2
                (start..count).map { "This is $it item." }
            }
        }
        return Observable.just(data).delay(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}