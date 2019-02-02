package com.cdts.oreo.extension

import android.util.Base64
import java.io.File
import java.io.InputStream

fun InputStream.toFile(path: String): File {
    val file = File(path)
    file.mkdirs()
    if (file.exists()) {
        file.delete()
    }
    this.use { input ->
        file.outputStream().use { fileOut ->
            input.copyTo(fileOut)
        }
    }
    return file
}

fun File.toBase64(): String {
    val bytes = this.readBytes()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}