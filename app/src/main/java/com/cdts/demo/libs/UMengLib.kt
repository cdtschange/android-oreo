package com.cdts.demo.libs

import android.content.Context
import com.cdts.demo.config.GlobalConfig
import com.cdts.demo.config.UMengConfig
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

object UMengLib {
    fun setup(context: Context) {
        UMConfigure.init(context, UMengConfig.appKey, GlobalConfig.channel, UMConfigure.DEVICE_TYPE_PHONE, "")
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
        if (!GlobalConfig.isRelease) {
            UMConfigure.setLogEnabled(true)
        }
    }
}