package com.cdts.oreo.data.encrypt

import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.EncryptUtils



fun String.md5(): String {
    return EncryptUtils.encryptMD5ToString(this)
}
fun String.sha1(): String {
    return EncryptUtils.encryptSHA1ToString(this)
}
fun String.sha224(): String {
    return EncryptUtils.encryptSHA224ToString(this)
}
fun String.sha256(): String {
    return EncryptUtils.encryptSHA256ToString(this)
}
fun String.sha384(): String {
    return EncryptUtils.encryptSHA384ToString(this)
}
fun String.sha512(): String {
    return EncryptUtils.encryptSHA512ToString(this)
}
fun String.aesEncrypt(key: String, iv: String?, padding: String = "AES/CFB/PKCS7Padding"): ByteArray? {
    return EncryptUtils.encryptAES(this.toByteArray(Charsets.UTF_8), key.toByteArray(Charsets.UTF_8), padding, iv?.toByteArray(Charsets.UTF_8)) ?: return null
}
fun ByteArray.aesDecrypt(key: String, iv: String?, padding: String = "AES/CFB/PKCS7Padding"): String? {
    val bytes = EncryptUtils.decryptAES(this, key.toByteArray(Charsets.UTF_8), padding, iv?.toByteArray(Charsets.UTF_8)) ?: return null
    return String(bytes, Charsets.UTF_8)
}

fun String.urlEncode(): String {
    return EncodeUtils.urlEncode(this)
}
fun String.urlDecode(): String {
    return EncodeUtils.urlDecode(this)
}
fun String.base64Encode(): String {
    return String(EncodeUtils.base64Encode(this), Charsets.UTF_8)
}
fun String.base64Decode(): String {
    return String(EncodeUtils.base64Decode(this), Charsets.UTF_8)
}