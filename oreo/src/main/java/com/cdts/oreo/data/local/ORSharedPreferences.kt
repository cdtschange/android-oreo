package com.cdts.oreo.data.local

import android.content.Context
import android.content.SharedPreferences
import com.cdts.oreo.ui.application.ORApplication

object ORSharedPreferences {

    private val preferences: SharedPreferences by lazy {
        val context = ORApplication.application!!
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Suppress("UNCHECKED_CAST")
    fun<T> get(key: String, def: T): T? {
        return when(def) {
            is String -> preferences.getString(key, def) as? T
            is Int -> preferences.getInt(key, def) as? T
            is Boolean -> preferences.getBoolean(key, def) as? T
            is Long -> preferences.getLong(key, def) as? T
            is Float -> preferences.getFloat(key, def) as? T
            else -> null
        }
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
