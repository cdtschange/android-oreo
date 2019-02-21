package com.cdts.demo.data.network.repository

import android.net.Uri
import android.os.Environment
import androidx.core.net.toFile
import com.cdts.demo.BuildConfig
import com.cdts.demo.R
import com.cdts.demo.data.network.data.*
import com.cdts.demo.schema.repository.BaseRepository
import com.cdts.oreo.data.network.retrofit.ORUploadMultipartFile
import com.cdts.oreo.extension.toFile
import com.cdts.oreo.ui.application.ORApplication
import io.reactivex.Observable


class NetworkRepository: BaseRepository() {

    fun fetchData(method: NetworkType): Observable<BaseNetApiModel> {
        when(method) {
            NetworkType.Get -> {
                return NetworkApi().signal(GetNetApiModel())
            }
            NetworkType.Post -> {
                return NetworkApi().signal(PostNetApiModel())
            }
            NetworkType.Put -> {
                return NetworkApi().signal(PutNetApiModel())
            }
            NetworkType.Delete -> {
                return NetworkApi().signal(DeleteNetApiModel())
            }
            NetworkType.MultipartUpload -> {
                val model = MultipartUploadNetApiModel()
                val fileUri = ORApplication.application!!.applicationContext.resources.openRawResource(R.raw.rainbow)
                val imageFile = fileUri.toFile(Environment.getExternalStorageDirectory().path + "/temp.jpg")
                val file = ORUploadMultipartFile(imageFile, "name", "fileName", "image/jpeg")
                model.files = listOf(file)

                return NetworkApi().signal(model)
            }
        }
    }
}
data class NetworkModel(val title: String, val detail: String)
enum class NetworkType {
    Get, Post, Put, Delete, MultipartUpload
}