package com.cdts.demo.data.cache.repository

import com.cdts.demo.schema.repository.BaseRepository
import com.cdts.oreo.data.local.ORCache
import com.cdts.oreo.data.local.ORDiskCache
import com.cdts.oreo.data.local.ORDiskCacheLiveData
import com.cdts.oreo.data.local.ORSharedPreferences

class CacheRepository: BaseRepository() {

    var cacheString: String? by ORCache()
    var cacheInt: Int? by ORCache()
    var cacheDouble: Double? by ORCache()
    var cacheBoolean: Boolean? by ORCache()

    var diskString: String? by ORDiskCache()
    var diskInt: Int? by ORDiskCache()
    var diskDouble: Double? by ORDiskCache()
    var diskBoolean: Boolean? by ORDiskCache()

    var sharedPreferencesString: String?
        get() = ORSharedPreferences.get("shared_preferences_string", String::class.java)
        set(value) {
            ORSharedPreferences.put("shared_preferences_string", value)
        }

    var sharedPreferencesInt: Int?
        get() = ORSharedPreferences.get("shared_preferences_int", Int::class.java)
        set(value) {
            ORSharedPreferences.put("shared_preferences_int", value)
        }

    var sharedPreferencesDouble: Double?
        get() = ORSharedPreferences.get("shared_preferences_double", Double::class.java)
        set(value) {
            ORSharedPreferences.put("shared_preferences_double", value)
        }

    var sharedPreferencesBoolean: Boolean?
        get() = ORSharedPreferences.get("shared_preferences_boolean", Boolean::class.java)
        set(value) {
            ORSharedPreferences.put("shared_preferences_boolean", value)
        }

    var liveDataString = ORDiskCacheLiveData<String?>("disk_cache_live_data_string")
    var liveDataInt = ORDiskCacheLiveData<Int?>("disk_cache_live_data_int")
    var liveDataDouble = ORDiskCacheLiveData<Double?>("disk_cache_live_data_double")
    var liveDataBoolean = ORDiskCacheLiveData<Boolean?>("disk_cache_live_data_boolean")
}

enum class CacheType {
    Cache, Disk, SharedPreferences, LiveData
}
data class CacheModel(val title: String, val detail: Any?)