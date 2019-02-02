package com.cdts.oreo.extension


fun String.queryDictionary(): Map<String, String> {
    var queryString = mutableMapOf<String, String>()
    for (item in this.split("&")) {
        val key = item.split("=").first()
        val value = item.split("=").last().replace("+", " ")
        queryString[key] = value
    }
    return queryString
}