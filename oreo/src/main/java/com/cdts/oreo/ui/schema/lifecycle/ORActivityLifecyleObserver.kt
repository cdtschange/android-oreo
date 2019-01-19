package com.cdts.oreo.ui.schema.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import com.cdts.oreo.ui.schema.view.ORBaseActivity
import timber.log.Timber

interface IORActivityResultLifecycleObserver {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}

open class ORActivityLifecycleObserver(val name: String): LifecycleObserver, IORActivityResultLifecycleObserver {

    private var resultObservers = mutableListOf<ORBaseActivity.OnResultObserverListener>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Timber.i("$name onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Timber.e("$name onDestroy")
    }

    fun addResultObserver(observer: ORBaseActivity.OnResultObserverListener) {
        if (!resultObservers.contains(observer)) {
            resultObservers.add(observer)
        }
    }

    fun removeResultObserver(observer: ORBaseActivity.OnResultObserverListener) {
        if (resultObservers.contains(observer)) {
            resultObservers.remove(observer)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        resultObservers.forEach {
            it.invoke(requestCode, resultCode, data)
        }
    }
}