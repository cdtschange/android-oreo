package com.cdts.oreo.data.network

import okhttp3.Response


enum class ORStatusCode constructor(val value: Int) {
    Default(0),
    Success(200),
    Canceled(-999),
    ValidateFailed(-99999),
    BadRequest(400),
    UnAuthorized(401),
    LoginOtherDevice(403)
}

class ORError(statusCode: Int, message: String) : Exception() {
    var statusCode: Int = 0
    override var message: String = ""
    var originalMessage: String = ""
    var response: Response? = null
    var defaultMessage: String = "网络异常"

    init {
        this.statusCode = statusCode
        this.message = message
        this.originalMessage = message
        if (message.isEmpty() || message.contains("<html")) {
            this.message = defaultMessage
        }
    }

    override fun toString(): String {
        return "statusCode: $statusCode, message: $message, originalMessage: $originalMessage, response: $response)"
    }

}
