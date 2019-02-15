package com.cdts.oreo.extension

import com.cdts.oreo.BaseTestCase
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateExTests: BaseTestCase() {
    @Test
    fun testDate() {
        val date = Date(0).format()
        assert(date == "1970-01-01 08:00:00")
    }
}