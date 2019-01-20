package com.cdts.oreo.extension

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

fun <T> Gson.typeTokenFromJson(json: JsonElement): T = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
fun <T> Gson.typeTokenFromJson(str: String): T = this.fromJson<T>(str, object: TypeToken<T>() {}.type)