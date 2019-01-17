package com.cdts.oreo.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val formatter = SimpleDateFormat(format)
    return formatter.format(this)
}