package com.cdts.demo.libs

import android.content.Context
import com.cdts.demo.config.GlobalConfig
import com.cdts.demo.config.UMengConfig
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import timber.log.Timber

object UMengLib {
    fun setup(context: Context) {
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
        UMConfigure.init(context, UMengConfig.appKey, GlobalConfig.channel, UMConfigure.DEVICE_TYPE_PHONE, UMengConfig.messageSecret)
        if (!GlobalConfig.isRelease) {
            UMConfigure.setLogEnabled(true)
        }
        val pushAgent = PushAgent.getInstance(context)
        pushAgent.register(DemoUmengRegisterCallback())
    }

    class DemoUmengRegisterCallback: IUmengRegisterCallback {
        override fun onSuccess(p0: String?) {
            Timber.i("推送注册成功 deviceToken: $p0")
        }

        override fun onFailure(p0: String?, p1: String?) {
            Timber.e("推送注册失败: $p0, $p1")
        }
    }
}