package com.cdts.oreo.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavDestination
import com.cdts.oreo.ui.application.ORApplication

object ORSharedPreferences {

    val preferences: SharedPreferences by lazy {
        val context = ORApplication.application!!
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Suppress("UNCHECKED_CAST")
    fun<T> get(key: String, type: Class<T>): T? {
        if (!preferences.contains(key)) {
            return null
        }
        return when(type::class) {
            String::class -> preferences.getString(key, "") as? T
            Int::class -> preferences.getInt(key, 0) as? T
            Boolean::class -> preferences.getBoolean(key, false) as? T
            Long::class -> preferences.getLong(key, 0L) as? T
            Float::class -> preferences.getFloat(key, 0.0f) as? T
            else -> null
        }
    }

    fun contains(key: String): Boolean {
        return preferences.contains(key)
    }

    fun<T> put(key: String, value: T) {
        val editor = preferences.edit()
        return when(value) {
            is String -> editor.putString(key, value).apply()
            is Int -> editor.putInt(key, value).apply()
            is Boolean -> editor.putBoolean(key, value).apply()
            is Long -> editor.putLong(key, value).apply()
            is Float -> editor.putFloat(key, value).apply()
            else -> return
        }
    }

    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
}
