package com.cdts.oreo.extension

import com.google.gson.GsonBuilder


fun String.queryDictionary(): Map<String, String> {
    val queryString = mutableMapOf<String, String>()
    for (item in this.split("&")) {
        val key = item.split("=").first()
        val value = item.split("=").last().replace("+", " ")
        queryString[key] = value
    }
    return queryString
}

fun Map<*, *>.toPrettyString(): String {
    return GsonBuilder().setPrettyPrinting().create().toJson(this)
}