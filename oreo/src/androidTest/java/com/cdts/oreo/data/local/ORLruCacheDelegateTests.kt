package com.cdts.oreo.data.local

import android.Manifest
import android.os.Environment
import android.support.test.InstrumentationRegistry
import android.support.test.rule.GrantPermissionRule
import com.cdts.oreo.BaseTestCase
import org.junit.Rule
import org.junit.Test


class ORLruCacheDelegateTests: BaseTestCase() {
    @get:Rule
    val permissionRuleWriteExternalStorage = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @get:Rule
    val permissionRuleReadExternalStorage = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE)

    private object TestCacheModel {
        var name: String? by ORCache()
        var sex: Boolean? by ORCache()
        var age: Int? by ORCache()
        var money: Double? by ORCache()
    }
    private object TestDiskCacheModel {
        var name: String? by ORDiskCache()
        var sex: Boolean? by ORDiskCache()
        var age: Int? by ORDiskCache()
        var money: Double? by ORDiskCache()
    }
    private object TestCacheLiveDataModel {
        var name = ORCacheLiveData<String?>("cache_live_data_name")
        var sex = ORCacheLiveData<Boolean?>("cache_live_data_sex")
        var age = ORCacheLiveData<Int?>("cache_live_data_age")
        var money = ORCacheLiveData<Double?>("cache_live_data_money")
    }
    private object TestDiskCacheLiveDataModel {
        var name = ORDiskCacheLiveData<String?>("disk_cache_live_data_name")
        var sex = ORDiskCacheLiveData<Boolean?>("disk_cache_live_data_sex")
        var age = ORDiskCacheLiveData<Int?>("disk_cache_live_data_age")
        var money = ORDiskCacheLiveData<Double?>("disk_cache_live_data_money")
    }

    @Test
    fun testCache() {
        assert(TestCacheModel.name == null)
        TestCacheModel.name = "abc"
        assert(TestCacheModel.name == "abc")
        TestCacheModel.name = null
        assert(TestCacheModel.name == null)

        assert(TestCacheModel.sex == null)
        TestCacheModel.sex = true
        assert(TestCacheModel.sex == true)
        TestCacheModel.sex = null
        assert(TestCacheModel.sex == null)

        assert(TestCacheModel.age == null)
        TestCacheModel.age = 123
        assert(TestCacheModel.age == 123)
        TestCacheModel.age = null
        assert(TestCacheModel.age == null)

        assert(TestCacheModel.money == null)
        TestCacheModel.money = 1.2
        assert(TestCacheModel.money == 1.2)
        TestCacheModel.money = null
        assert(TestCacheModel.money == null)
    }

    @Test
    fun testDiskCache() {
        assert(TestDiskCacheModel.name == null)
        TestDiskCacheModel.name = "abc"
        assert(TestDiskCacheModel.name == "abc")
        TestDiskCacheModel.name = null
        assert(TestDiskCacheModel.name == null)

        assert(TestDiskCacheModel.sex == null)
        TestDiskCacheModel.sex = true
        assert(TestDiskCacheModel.sex == true)
        TestDiskCacheModel.sex = null
        assert(TestDiskCacheModel.sex == null)

        assert(TestDiskCacheModel.age == null)
        TestDiskCacheModel.age = 123
        assert(TestDiskCacheModel.age == 123)
        TestDiskCacheModel.age = null
        assert(TestDiskCacheModel.age == null)

        assert(TestDiskCacheModel.money == null)
        TestDiskCacheModel.money = 1.2
        assert(TestDiskCacheModel.money == 1.2)
        TestDiskCacheModel.money = null
        assert(TestDiskCacheModel.money == null)
    }


    @Test
    fun testCacheLiveData() {
        assert(TestDiskCacheLiveDataModel.name.value == null)
        TestDiskCacheLiveDataModel.name.value = "abc"
        assert(TestDiskCacheLiveDataModel.name.value == "abc")
        TestDiskCacheLiveDataModel.name.value = null
        assert(TestDiskCacheLiveDataModel.name.value == null)

        assert(TestDiskCacheLiveDataModel.sex.value == null)
        TestDiskCacheLiveDataModel.sex.value = true
        assert(TestDiskCacheLiveDataModel.sex.value == true)
        TestDiskCacheLiveDataModel.sex.value = null
        assert(TestDiskCacheLiveDataModel.sex.value == null)

        assert(TestDiskCacheLiveDataModel.age.value == null)
        TestDiskCacheLiveDataModel.age.value = 123
        assert(TestDiskCacheLiveDataModel.age.value == 123)
        TestDiskCacheLiveDataModel.age.value = null
        assert(TestDiskCacheLiveDataModel.age.value == null)

        assert(TestDiskCacheLiveDataModel.money.value == null)
        TestDiskCacheLiveDataModel.money.value = 1.2
        assert(TestDiskCacheLiveDataModel.money.value == 1.2)
        TestDiskCacheLiveDataModel.money.value = null
        assert(TestDiskCacheLiveDataModel.money.value == null)
    }


    @Test
    fun testDiskCacheLiveData() {
        assert(TestCacheLiveDataModel.name.value == null)
        TestCacheLiveDataModel.name.value = "abc"
        assert(TestCacheLiveDataModel.name.value == "abc")
        TestCacheLiveDataModel.name.value = null
        assert(TestCacheLiveDataModel.name.value == null)

        assert(TestCacheLiveDataModel.sex.value == null)
        TestCacheLiveDataModel.sex.value = true
        assert(TestCacheLiveDataModel.sex.value == true)
        TestCacheLiveDataModel.sex.value = null
        assert(TestCacheLiveDataModel.sex.value == null)

        assert(TestCacheLiveDataModel.age.value == null)
        TestCacheLiveDataModel.age.value = 123
        assert(TestCacheLiveDataModel.age.value == 123)
        TestCacheLiveDataModel.age.value = null
        assert(TestCacheLiveDataModel.age.value == null)

        assert(TestCacheLiveDataModel.money.value == null)
        TestCacheLiveDataModel.money.value = 1.2
        assert(TestCacheLiveDataModel.money.value == 1.2)
        TestCacheLiveDataModel.money.value = null
        assert(TestCacheLiveDataModel.money.value == null)
    }
}