package com.cdts.oreo.ui.schema.viewmodel

import android.database.DataSetObservable
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData

open class ORBaseListViewModel: ORBaseViewModel() {

    val dataSetObservable = DataSetObservable()

    private val mLock = Any()
    open var dataIndex = 0
    open var listLoadNumber = 20
    open var listMaxNumber= Int.MAX_VALUE
    val dataArray: MutableLiveData<MutableList<Any>> = MutableLiveData()

    fun appendDataArray(collection: Collection<Any>) {
        synchronized(mLock) {
            val temp = dataArray.value ?: mutableListOf()
            if (dataIndex == 0) {
                temp.clear()
            }
            temp.addAll(collection)
            dataArray.value = temp
        }
        notifyDataSetChanged()
    }

    val canLoadMoreData: Boolean
        get() = (dataArray.value?.size ?: 0) % listLoadNumber == 0
                && (dataArray.value?.size ?: 0) < listMaxNumber

    fun notifyDataSetChanged() {
        dataSetObservable.notifyChanged()
    }
    fun notifyDataSetInvalidated() {
        dataSetObservable.notifyInvalidated()
    }

}