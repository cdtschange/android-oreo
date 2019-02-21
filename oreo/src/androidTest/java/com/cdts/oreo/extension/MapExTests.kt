package com.cdts.oreo.extension

import com.cdts.oreo.BaseTestCase
import org.junit.Test

class MapExTests: BaseTestCase() {
    @Test
    fun testMap() {
        val map = mapOf("a" to "b")
        print(map.toPrettyString())
    }
}