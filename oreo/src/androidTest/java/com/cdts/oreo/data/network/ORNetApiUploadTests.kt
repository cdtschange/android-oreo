package com.cdts.oreo.data.network

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.test.rule.GrantPermissionRule
import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.data.network.retrofit.ORHttpMethod
import com.cdts.oreo.data.network.retrofit.ORRequestType
import com.cdts.oreo.data.network.retrofit.ORUploadMultipartFile
import com.cdts.oreo.extension.toBase64
import com.cdts.oreo.extension.toFile
import org.junit.Rule
import org.junit.Test


class ORNetApiUploadTests: BaseTestCase() {
    @get:Rule
    val permissionRuleWriteExternalStorage = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    @get:Rule
    val permissionRuleReadExternalStorage = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE)

    @Test
    fun testMultipartUpload() {
        val apiModel = TestUploadMultipartNetApiModel()
        val api = TestNetApi()
        api.baseUrlString = NetApiTestConstant.urlString
        api.baseHeaders = NetApiTestConstant.baseHeaders
        api.baseParams = NetApiTestConstant.baseParams
        apiModel.url = "post"
        apiModel.method = ORHttpMethod.POST
        apiModel.params = NetApiTestConstant.unicodeParams
        val fileUri = javaClass.classLoader.getResourceAsStream("drawable/rainbow.jpg")
        val imageFile = fileUri.toFile(Environment.getExternalStorageDirectory().path + "/temp.jpg")
        val file = ORUploadMultipartFile(imageFile, "testName", "testFile", "image/jpeg")
        apiModel.files = listOf(file)
        api.signal(apiModel, ORRequestType.MultipartUpload).subscribe({ data ->
            val headers = data.result!!["headers"] as Map<String, String>
            NetApiTestConstant.baseHeaders.forEach {
                assert(headers[it.key] == it.value)
            }
            val params = data.result!!["form"] as Map<String, String>
            assert(params.count() == NetApiTestConstant.baseParams.count() + NetApiTestConstant.params.count())
            assert(params[NetApiTestConstant.baseParams.keys.first()] == NetApiTestConstant.baseParams.values.first())
            assert(params[NetApiTestConstant.params.keys.first()] == NetApiTestConstant.params.values.first())

            val files = data.result!!["files"] as Map<String, String>
            assert(files.count() == 1)
            assert(files[file.name]!!.contains(imageFile.toBase64()))
            signal()
        }, {
            assert(false)
            signal()
        })

        await()
    }
}
