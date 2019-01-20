package com.cdts.oreo.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit


@SuppressLint("CheckResult")
fun View.addClickAction(action: (view: View) -> Unit) {
    //隐藏键盘
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    //防抖
    imm.hideSoftInputFromWindow(this.windowToken, 0)
    RxView.clicks(this)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe { action(this) }
}

fun View.getActivity(): Activity {
    val context = this.context
    try {
        var baseContext = context as ContextWrapper
        while (baseContext !is Activity) {
            val contextWrapper = baseContext.baseContext as ContextWrapper
            if (contextWrapper === baseContext) {
                throw RuntimeException("Context 转Activity 异常")
            }
            baseContext = contextWrapper
        }
        return baseContext
    } catch (throwable: Throwable) {
        throw RuntimeException("Context 转Activity 异常")
    }
}