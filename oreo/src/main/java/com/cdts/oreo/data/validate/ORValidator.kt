package com.cdts.oreo.data.validate

import com.cdts.oreo.data.network.ORError
import com.cdts.oreo.data.network.ORStatusCode
import io.reactivex.Observable


object ORValidator {
    data class V2(val expression: Boolean, val message: String)
    data class V3(val expression: Boolean, val code: Int, val message: String)

    fun validate(conditions: Array<V2>): Observable<Unit> {
        val cond = conditions.map { V3(it.expression, ORStatusCode.ValidateFailed.value, it.message) }.toTypedArray()
        return validate(cond)
    }
    fun validate(conditions: Array<V3>): Observable<Unit> {
        for (condition in conditions) {
            if (condition.expression) {
                val error = ORError(condition.code, condition.message)
                return Observable.error(error)
            }
        }
        return Observable.just(Unit)
    }

}