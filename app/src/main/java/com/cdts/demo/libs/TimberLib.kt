package com.pintec.velo.libs

import android.util.Log
import com.cdts.demo.config.GlobalConfig
import timber.log.Timber
import timber.log.Timber.DebugTree



object TimberLib {
    fun setup() {
        if (!GlobalConfig.isRelease) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            FakeCrashLibrary.log(priority, tag, message)

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t)
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t)
                }
            }
        }
    }
}

/** Not a real crash reporting library!  */
private class FakeCrashLibrary private constructor() {

    init {
        throw AssertionError("No instances.")
    }

    companion object {
        fun log(priority: Int, tag: String?, message: String) {

        }

        fun logWarning(t: Throwable) {

        }

        fun logError(t: Throwable) {

        }
    }
}