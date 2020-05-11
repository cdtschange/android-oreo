package com.cdts.oreo.data.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.test.MainTestActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ORSharedPreferencesTests: BaseTestCase() {

    @get:Rule
    var activityRule: ActivityTestRule<MainTestActivity> = ActivityTestRule(MainTestActivity::class.java,true,true)

    @Test
    fun testPreferences() {
        ORSharedPreferences.clear()
        testSingle("test_key_string", "abc", String::class.java)
        testSingle("test_key_int", 123, Int::class.java)
        testSingle("test_key_bool", true, Boolean::class.java)
        testSingle("test_key_double", 1.2, Double::class.java)
        testSingle("test_key_list", arrayListOf("a", "b"), ArrayList::class.java)
        testSingle("test_key_list_int", arrayListOf(1, 2, 3), ArrayList::class.java)
        testSingle("test_key_dict", mapOf("a" to "a1", "b" to "b1"), Map::class.java)
    }

    private fun<T> testSingle(key: String, value: T, type: Class<T>) {
        assert(!ORSharedPreferences.contains(key))
        var t: T? = ORSharedPreferences.get(key, type)
        assert(t == null)
        ORSharedPreferences.put(key, value)
        assert(ORSharedPreferences.contains(key))
        t = ORSharedPreferences.get(key, type)
        assert(t == value)
        ORSharedPreferences.remove(key)
        assert(!ORSharedPreferences.contains(key))

    }
}