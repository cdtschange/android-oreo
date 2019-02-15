package com.cdts.oreo.extension

import com.cdts.oreo.BaseTestCase
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.junit.Test

class GsonExTests: BaseTestCase() {
    @Test
    fun testGson() {
        val json = JsonObject()
        json.addProperty("a", "a1")
        var map: Map<String, String> = Gson().typeTokenFromJson(json)
        assert(map.size == 1)
        assert(map["a"] == "a1")
        map = Gson().typeTokenFromJson(Gson().toJson(json))
        assert(map.size == 1)
        assert(map["a"] == "a1")
    }
}