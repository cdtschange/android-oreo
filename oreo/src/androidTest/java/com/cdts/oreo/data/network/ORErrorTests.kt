package com.cdts.oreo.data.network

import com.cdts.oreo.BaseTestCase
import org.junit.Test


class ORErrorTests: BaseTestCase() {
    @Test
    fun testORError() {
        val code = ORStatusCode.BadRequest.value
        val message = "test"
        val error = ORError(code, message)
        assert(error.statusCode == code)
        assert(error.originalMessage == message)
        assert(error.message == message)
        assert(error.response == null)
        assert(error.toString().contains("statusCode"))
        assert(error.toString().contains("message"))

        val httpMessage = "<raw>"
        val httpError = ORError(code, httpMessage)
        assert(httpError.statusCode == code)
        assert(httpError.originalMessage == httpMessage)
        assert(httpError.message == httpError.defaultMessage)

        val emptyError = ORError(code, "")
        assert(emptyError.statusCode == code)
        assert(emptyError.originalMessage == "")
        assert(emptyError.message == httpError.defaultMessage)
    }
}