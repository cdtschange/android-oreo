package com.cdts.oreo.data.permission

import android.annotation.SuppressLint
import android.os.Looper
import com.cdts.oreo.ui.router.ORRouter
import com.tbruyelle.rxpermissions2.RxPermissions


object ORPermission {

    private var complete: (success: Boolean) -> Unit = {}

    fun guard(perms: String, complete: (success: Boolean) -> Unit) {
        guard(arrayOf(perms), complete)
    }
    @SuppressLint("CheckResult")
    fun guard(perms: Array<String>, complete: (success: Boolean) -> Unit) {
        ORPermission.complete = complete
        val rxPermissions = RxPermissions(ORRouter.topActivity())
        if (Looper.getMainLooper() != Looper.myLooper()) {
            ORRouter.topActivity().runOnUiThread {
                rxPermissions.request(*perms).subscribe ({ granted ->
                    complete(granted)
                }, {
                    complete(false)
                })
            }
        } else {
            rxPermissions.request(*perms).subscribe ({ granted ->
                complete(granted)
            }, {
                complete(false)
            })
        }
    }
}