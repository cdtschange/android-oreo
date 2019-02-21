package com.cdts.oreo.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import com.cdts.oreo.data.permission.ORPermission
import com.cdts.oreo.ui.application.ORApplication
import github.nisrulz.easydeviceinfo.base.EasyLocationMod
import timber.log.Timber
import android.location.Geocoder
import java.util.*


object ORLocation {
    @SuppressLint("MissingPermission")
    fun requestLocation(completion: (success: Boolean, latitude: Double?, longitude: Double?) -> Unit) {
        ORPermission.guard(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) { success ->
            if (success) {
                val value = EasyLocationMod(ORApplication.application).latLong
                Timber.i("Get Location: $value")
                val latitude = value[0]
                val longitude = value[1]
                completion(success, latitude, longitude)
            } else {
                Timber.e("Get Location: not allowed")
                completion(success, null, null)
            }
        }
    }

    fun transferToPlace(latitude: Double, longitude: Double): Address? {
        val gcd = Geocoder(ORApplication.application, Locale.getDefault())
        val addresses: List<Address>
        return try {
            addresses = gcd.getFromLocation(
                latitude,
                longitude, 1
            )
            addresses.firstOrNull()
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}