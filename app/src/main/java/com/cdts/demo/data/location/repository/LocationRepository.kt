package com.cdts.demo.data.location.repository

import com.cdts.demo.schema.repository.BaseRepository
import com.cdts.oreo.data.location.ORLocation
import io.reactivex.Observable

class LocationRepository: BaseRepository() {

    var currentLocation: String? = null
    var address: String? = null

    fun fetchData(): List<LocationModel> {
        return listOf(
            LocationModel("Current Location", currentLocation),
            LocationModel("Current Address", address)
        )
    }

    fun fetchSystemLocation(): Observable<Boolean> {
        return Observable.create { sink ->
            ORLocation.requestLocation { success, latitude, longitude ->
                currentLocation = if (success) {
                    "($latitude, $longitude)"
                } else {
                    ""
                }
                address = if (success && latitude != null && longitude != null) {
                    ORLocation.transferToPlace(latitude, longitude)?.toString() ?: ""
                } else {
                    ""
                }
                sink.onNext(success)
                sink.onComplete()
            }
        }
    }
}

data class LocationModel(val title: String, val detail: Any?)