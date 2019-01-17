package com.cdts.oreo.extension

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: JsonElement): T = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
inline fun <reified T> Gson.fromJson(str: String): T = this.fromJson<T>(str, object: TypeToken<T>() {}.type)