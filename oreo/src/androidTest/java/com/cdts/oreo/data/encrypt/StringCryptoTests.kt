package com.cdts.oreo.data.encrypt

import com.cdts.oreo.BaseTestCase
import org.junit.Test


class ORErrorTests: BaseTestCase() {
    @Test
    fun testEncrypt() {
        val value = "Hello world!"
        assert(value.md5() == "86fb269d190d2c85f6e0468ceca42a20")
        assert(value.sha1() == "d3486ae9136e7856bc42212385ea797094475802")
        assert(value.sha224() == "7e81ebe9e604a0c97fef0e4cfe71f9ba0ecba13332bde953ad1c66e4")
        assert(value.sha256() == "c0535e4be2b79ffd93291305436bf889314e4a3faec05ecffcbb7df31ad9e51a")
        assert(value.sha384() == "86255fa2c36e4b30969eae17dc34c772cbebdfc58b58403900be87614eb1a34b8780263f255eb5e65ca9bbb8641cccfe")
        assert(value.sha512() == "f6cde2a0f819314cdde55fc227d8d7dae3d28cc556222a0a8ad66d91ccad4aad6094f517a2182360c9aacf6a3dc323162cb6fd8cdffedb0fe038f55e85ffb5b6")
    }
    @Test
    fun testUrlEncode() {
        val value = "https://www.demo.cc?name=demo&title=测试+"
        val encode = "https%3A%2F%2Fwww.demo.cc%3Fname%3Ddemo%26title%3D%E6%B5%8B%E8%AF%95%2B"
        assert(value.urlEncode() == encode)
        assert(encode.urlDecode() == value)
    }

    @Test
    fun testBase64Encode() {
        val value = "Hello world!"
        val encode = "SGVsbG8gd29ybGQh"
        assert("🔥💧".base64Decode() == null)
        assert(value.base64Encode() == encode)
        assert(encode.base64Decode() == value)
    }

    @Test
    fun testAES() {
        val value = "11111111111111111111111111111111"
        val key = "11111111111111111111111111111111"
        val iv = "1111111111111111"
        val encode = "77+9WHHvv71XCGEhIh3vv73vv73vv71Y77+9GUk077+9blrvv71bZ++/ve+/ve+/vUHvv71B77+9Eu+/vVIL77+9cRnvv71oTGfvv73vv71n77+9NnY="
        assert(value.aesEncrypt("1234", "1234") == null)
        assert(encode.toByteArray().aesDecrypt("1234", "1234") == null)
        val bytes = value.aesEncrypt(key, iv)
        assert(String(bytes!!).base64Encode() == encode)
        assert(bytes.aesDecrypt(key, iv) == value)
    }
}