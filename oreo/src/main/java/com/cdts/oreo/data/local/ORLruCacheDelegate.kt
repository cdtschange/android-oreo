package com.cdts.oreo.data.local

import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ORCache<T>: ReadWriteProperty<Any?, T?> {
    /**
     * value type support: Int, Boolean, String, Float, Long, Serializable Object
     * value must be nullable
     */
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        ORLruCache.getCache(ORLruCache.CacheType.Cache).put(property.name, value)
    }
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return ORLruCache.getCache(ORLruCache.CacheType.Cache).get(property.name)
    }
}

class ORDiskCache<T>: ReadWriteProperty<Any?, T?> {
    /**
     * value type support: Int, Boolean, String, Float, Long, Serializable Object
     * value must be nullable
     */
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        ORLruCache.getCache(ORLruCache.CacheType.Disk).put(property.name, value)
    }
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return ORLruCache.getCache(ORLruCache.CacheType.Disk).get(property.name)
    }
}

class ORCacheLiveData<T>(private val cacheName: String): MutableLiveData<T>() {
    /**
     * value type support: Int, Boolean, String, Float, Long, Serializable Object
     * value must be nullable
     */
    override fun setValue(value: T?) {
        ORLruCache.getCache(ORLruCache.CacheType.Cache).put(cacheName, value)
        super.setValue(value)
    }

    override fun getValue(): T? {
        if (super.getValue() == null) {
            return ORLruCache.getCache(ORLruCache.CacheType.Cache).get(cacheName)
        }
        return super.getValue()
    }
}

class ORDiskCacheLiveData<T>(private val cacheName: String): MutableLiveData<T>() {
    /**
     * value type support: Int, Boolean, String, Float, Long, Serializable Object
     * value must be nullable
     */
    override fun setValue(value: T?) {
        ORLruCache.getCache(ORLruCache.CacheType.Disk).put(cacheName, value)
        super.setValue(value)
    }

    override fun getValue(): T? {
        if (super.getValue() == null) {
            return ORLruCache.getCache(ORLruCache.CacheType.Disk).get(cacheName)
        }
        return super.getValue()
    }
}
