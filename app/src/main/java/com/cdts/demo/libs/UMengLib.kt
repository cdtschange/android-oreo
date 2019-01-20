package com.cdts.demo.libs

import android.content.Context
import com.cdts.demo.config.GlobalConfig
import com.cdts.demo.config.UMengConfig
import com.umeng.commonsdk.UMConfigure

object UMengLib {
    fun setup(context: Context) {
        UMConfigure.init(context, UMengConfig.appKey, GlobalConfig.channel, UMConfigure.DEVICE_TYPE_PHONE, "")
        if (!GlobalConfig.isRelease) {
            UMConfigure.setLogEnabled(true)
        }
    }
}