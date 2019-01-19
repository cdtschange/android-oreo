package com.cdts.oreo.extension

import android.annotation.SuppressLint
import android.content.Context
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