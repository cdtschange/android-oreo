package com.cdts.oreo.data.location

import android.Manifest
import android.annotation.SuppressLint
import com.cdts.oreo.data.permission.ORPermission
import com.cdts.oreo.ui.application.ORApplication
import github.nisrulz.easydeviceinfo.base.EasyLocationMod
import timber.log.Timber

object ORLocation {
    @SuppressLint("MissingPermission")
    fun refreshLocation(completion: (Double?, Double?, Boolean) -> Unit) {
        ORPermission.guard(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) { success ->
            if (success) {
                val value = EasyLocationMod(ORApplication.application).latLong
                Timber.i("Get Location: $value")
                val latitude = value[0]
                val longitude = value[1]
                completion(latitude, longitude, success)
            } else {
                Timber.e("Get Location: not allowed")
                completion(null, null, success)
            }
        }
    }
}