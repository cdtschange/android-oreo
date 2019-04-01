package com.cdts.oreo.data.crash

import android.content.pm.PackageManager
import android.os.Build
import com.cdts.oreo.ui.application.ORApplication
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*


class ORCrashHandler() : Thread.UncaughtExceptionHandler {

    companion object {
        val shared = ORCrashHandler()
    }

    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    private var handler: ((thread: Thread, ex: Throwable, message: String) -> Unit)? = null

    fun remove() {
        Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler)
        mDefaultHandler = null

    }

    fun registerExceptionHandler(callback: (thread: Thread, ex: Throwable, message: String) -> Unit) {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
        handler = callback
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(thread, ex) && mDefaultHandler != null) {
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {

            }

            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        }
    }


    private fun handleException(thread: Thread, ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        handler?.invoke(thread, ex, getCrashInfo(ex))
        return true
    }

    private fun collectDeviceInfo(): Map<String, String> {
        val infos = HashMap<String, String>()
        try {
            val pm = ORApplication.application.packageManager
            val pi = pm.getPackageInfo(
                ORApplication.application.packageName,
                PackageManager.GET_ACTIVITIES
            )
            if (pi != null) {
                val versionName = if (pi.versionName == null)
                    "null"
                else
                    pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                infos["versionName"] = versionName
                infos["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {

        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field.get(null).toString()
            } catch (e: Exception) {
            }

        }
        return infos
    }
    private fun getCrashInfo(ex: Throwable): String {

        val sb = StringBuffer()
        val infos = collectDeviceInfo()
        for ((key, value) in infos) {
            sb.append("$key=$value\n")
        }

        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)

        return sb.toString()
    }
}