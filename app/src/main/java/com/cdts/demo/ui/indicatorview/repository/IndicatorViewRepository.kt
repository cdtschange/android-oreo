package com.cdts.demo.ui.indicatorview.repository

import com.cdts.demo.schema.repository.BaseRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class IndicatorViewRepository: BaseRepository() {

    fun fetchData(): Observable<List<String>> {
        return Observable.just(listOf(
            "Indeterminate mode",
            "With label",
            "With details label",
            "Determinate mode",
            "Annular determinate mode",
            "Bar determinate mode",
            "Determinate mode with cancel",
            "Tip",
            "Tip at bottom",
            "Custom view with image"
        ))
    }
}