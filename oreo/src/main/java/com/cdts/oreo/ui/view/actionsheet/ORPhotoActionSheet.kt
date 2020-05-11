package com.cdts.oreo.ui.view.actionsheet

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.cdts.oreo.data.network.ORError
import com.cdts.oreo.data.network.ORStatusCode
import com.cdts.oreo.data.permission.ORPermission
import com.cdts.oreo.ui.router.ORRouter
import com.cdts.oreo.ui.schema.view.ORBaseActivity
import com.flyco.dialog.widget.ActionSheetDialog
import java.io.File

object ORPhotoActionSheet {

    private const val REQUEST_CODE_GALLERY = 0xa0
    private const val REQUEST_CODE_CAMERA = 0xa1
    private const val REQUEST_CODE_FILE = 0xa2

    fun showPhoto(activity: Activity, completion: (success: Boolean, uri: Uri?, error: ORError?) -> Unit): ActionSheetDialog {

        val items = arrayOf("Camera", "Image Library")
        val dialog = ActionSheetDialog(ORRouter.topActivity(), items, null)
        dialog.isTitleShow(false)//
            .titleBgColor(Color.parseColor("#ffffff"))
            .lvBgColor(Color.parseColor("#ffffff"))
            .lvBgColor(Color.parseColor("#ffffff"))
            .cancelText("Cancel")
            .layoutAnimation(null)
            .titleTextSize_SP(14.5f)//
            .show()

        dialog.setOnOperItemClickL { parent, view, position, id ->
            when(items[position]) {
                "Camera" -> showCamera(activity, completion)
                "Image Library" -> showLibrary(activity, completion)
            }
            dialog.dismiss()
        }
        return dialog
    }

    fun showCamera(activity: Activity, completion: (success: Boolean, uri: Uri?, error: ORError?) -> Unit) {

        ORPermission.guard(
                arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )) { success ->
            if (success) {
                try {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val photoFile = File(directory, "tmp_${System.currentTimeMillis()}.jpg")
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                        /**Android 7.0以上的方式**/
                        val contentUri = FileProvider.getUriForFile(activity, "${activity.packageName}.fileprovider", photoFile)
                        activity.grantUriPermission(activity.packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                    } else {
                        /**Android 7.0以下的方式**/
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
                    }
                    (activity as? ORBaseActivity)?.let {
                        val observer: ORBaseActivity.OnResultObserverListener = object : ORBaseActivity.OnResultObserverListener {
                            override fun invoke(requestCode: Int, resultCode: Int, data: Intent?) {
                                if (requestCode == REQUEST_CODE_CAMERA) {
                                    when (resultCode) {
                                        RESULT_OK -> completion(true, Uri.fromFile(photoFile), null)
                                        RESULT_CANCELED -> completion(false, null, ORError(resultCode, "取消操作"))
                                        else -> completion(false, null, ORError(resultCode, "访问相机错误"))
                                    }
                                }
                                it.removeResultObserver(this)
                            }
                        }
                        it.addResultObserver(observer)
                    }
                    activity.startActivityForResult(intent, REQUEST_CODE_CAMERA)
                } catch (e: Error){
                    val flag = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                    if (PackageManager.PERMISSION_GRANTED != flag) {
                        completion(false, null, ORError(ORStatusCode.BadRequest.value, "请先开启相机和写入文件访问权限"))
                    }
                }
            } else {
                completion(false, null, ORError(ORStatusCode.BadRequest.value, "请先开启相机和写入文件访问权限"))
            }
        }
    }

    fun showLibrary(activity: Activity, completion: (success: Boolean, uri: Uri?, error: ORError?) -> Unit) {
        ORPermission.guard(Manifest.permission.WRITE_EXTERNAL_STORAGE){
            success ->
            if (success) {
                val intentFromGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")

                (activity as? ORBaseActivity)?.let {
                    val observer: ORBaseActivity.OnResultObserverListener = object : ORBaseActivity.OnResultObserverListener {
                        override fun invoke(requestCode: Int, resultCode: Int, data: Intent?) {
                            if (requestCode == REQUEST_CODE_GALLERY) {
                                if (resultCode == RESULT_OK) {
                                    completion(true, data?.data, null)
                                } else {
                                    completion(false, null, ORError(resultCode, "选择图片错误"))
                                }
                            }
                            it.removeResultObserver(this)
                        }
                    }
                    it.addResultObserver(observer)
                }
                activity.startActivityForResult(intentFromGallery, REQUEST_CODE_GALLERY)
            } else {
                completion(false, null, ORError(ORStatusCode.BadRequest.value, "请先开启写入文件访问权限"))
            }
        }
    }

    fun showFile(activity: Activity, completion: (success: Boolean, uri: Uri?, error: ORError?) -> Unit) {
        ORPermission.guard(Manifest.permission.WRITE_EXTERNAL_STORAGE){
            success ->
            if (success) {
                val fileIntent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                fileIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                (activity as? ORBaseActivity)?.let {
                    val observer: ORBaseActivity.OnResultObserverListener = object : ORBaseActivity.OnResultObserverListener {
                        override fun invoke(requestCode: Int, resultCode: Int, data: Intent?) {
                            if (requestCode == REQUEST_CODE_FILE) {
                                if (resultCode == RESULT_OK) {
                                    completion(true, data?.data, null)
                                } else {
                                    completion(false, null, ORError(resultCode, "选择文件错误"))
                                }
                            }
                            it.removeResultObserver(this)
                        }
                    }
                    it.addResultObserver(observer)
                }
                activity.startActivityForResult(fileIntent, REQUEST_CODE_FILE)
            } else {
                completion(false, null, ORError(ORStatusCode.BadRequest.value, "请先开启写入文件访问权限"))
            }
        }
    }


}