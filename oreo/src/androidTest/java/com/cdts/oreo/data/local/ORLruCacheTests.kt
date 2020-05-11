package com.cdts.oreo.data.local

import android.Manifest
import android.os.Environment
import androidx.test.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.cdts.oreo.BaseTestCase
import org.junit.Rule
import org.junit.Test


class ORLruCacheTests: BaseTestCase() {
    @get:Rule
    val permissionRuleWriteExternalStorage = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @get:Rule
    val permissionRuleReadExternalStorage = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE)

    @Test
    fun testCache() {
        print(ORLruCache.appVersion)
        print(ORLruCache.maxSize)
        var cache = ORLruCache(Environment.getExternalStorageDirectory())
        print(cache.getCache())
        cache = ORLruCache("test", InstrumentationRegistry.getContext(), ORLruCache.CacheType.Cache)
        print(cache.getCache())
        cache.clear()
        testSingle(cache, "test_key_string", "abc")
        testSingle(cache, "test_key_int", 123)
        testSingle(cache, "test_key_bool", true)
        testSingle(cache, "test_key_double", 1.2)
        testSingle(cache, "test_key_list", arrayListOf("a", "b"))
        testSingle(cache, "test_key_list_int", arrayListOf(1, 2, 3))
        testSingle(cache, "test_key_dict", mapOf("a" to "a1", "b" to "b1"))
    }

    @Test
    fun testDisk() {
        val cache = ORLruCache("test", InstrumentationRegistry.getContext(), ORLruCache.CacheType.Disk)
        print(cache.getCache())
        cache.clear()
        testSingle(cache, "test_key_string", "abc")
        testSingle(cache, "test_key_int", 123)
        testSingle(cache, "test_key_bool", true)
        testSingle(cache, "test_key_double", 1.2)
        testSingle(cache, "test_key_list", arrayListOf("a", "b"))
        testSingle(cache, "test_key_list_int", arrayListOf(1, 2, 3))
        testSingle(cache, "test_key_dict", mapOf("a" to "a1", "b" to "b1"))
    }

    private fun<T> testSingle(cache: ORLruCache, key: String, value: T) {
        assert(!cache.contains(key))
        assert(cache.get<T>(key) == null)
        cache.put(key, value)
        assert(cache.contains(key))
        assert(cache.get<T>(key) == value)
        cache.remove(key)
        assert(!cache.contains(key))

    }
}