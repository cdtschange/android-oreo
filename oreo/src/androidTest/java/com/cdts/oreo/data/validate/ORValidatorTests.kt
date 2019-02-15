package com.cdts.oreo.data.validate

import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.data.network.ORError
import com.cdts.oreo.data.network.ORStatusCode
import org.junit.Test


class ORValidatorTests: BaseTestCase() {
    @Test
    fun testValidator() {
        ORValidator.validate(arrayOf(ORValidator.V2(true, "error1"))).subscribe({
            assert(false)
        }, { error ->
            assert((error as ORError).statusCode == ORStatusCode.ValidateFailed.value)
            assert((error).message == "error1")
        })
    }

    @Test
    fun testValidatorList() {
        ORValidator.validate(arrayOf(
            ORValidator.V2(false, "error1"),
            ORValidator.V2(true, "error2"))).subscribe({
            assert(false)
        }, { error ->
            assert((error as ORError).statusCode == ORStatusCode.ValidateFailed.value)
            assert((error).message == "error2")
        })

    }

    @Test
    fun testValidatorSuccess() {
        ORValidator.validate(arrayOf(
            ORValidator.V2(false, "error1"),
            ORValidator.V2(false, "error2"))).subscribe({
        }, {
            assert(false)
        })

    }
}