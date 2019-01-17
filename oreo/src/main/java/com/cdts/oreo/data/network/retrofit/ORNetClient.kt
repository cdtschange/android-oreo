package com.cdts.oreo.data.network.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import com.cdts.oreo.ui.application.ORApplication
import timber.log.Timber

enum class ORNetworkStatus {
    Unknown, Data2G, Data3G, Data4G, Data5G, Wifi, NotReachable;

    var reachableWifi: Boolean = false
        get() = this == Wifi

    var reachableCellular: Boolean = false
        get() = this == Data2G || this == Data3G ||this == Data4G ||this == Data5G
}

object ORNetClient {
    /**
     * 当前网络是否可用
     */
    val networkConnected: Boolean
        get() {
            val manager = ORApplication.application!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return manager.activeNetworkInfo?.isConnectedOrConnecting ?: false
        }
    /**
     * 当前WIFI是否可用
     */
    val wifiConnected: Boolean
        get() {
            val manager = ORApplication.application!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return (info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI)
        }

    /**
     * 当前网络的连接类型
     */
    private val networkClass: Int?
        get() {
            val manager = ORApplication.application!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netWorkInfo = manager.activeNetworkInfo
            return if (netWorkInfo != null && netWorkInfo.isAvailable && netWorkInfo.isConnected) {
                netWorkInfo.type
            } else {
                null
            }
        }
    /**
     * 当前网络的连接类型
     */
    val networkStatus: ORNetworkStatus
        get() {
            if (!networkConnected) return ORNetworkStatus.NotReachable
            if (wifiConnected) return ORNetworkStatus.Wifi
            return when (networkClass) {
                TelephonyManager.NETWORK_TYPE_GPRS,
                TelephonyManager.NETWORK_TYPE_EDGE,
                TelephonyManager.NETWORK_TYPE_CDMA,
                TelephonyManager.NETWORK_TYPE_1xRTT,
                TelephonyManager.NETWORK_TYPE_IDEN -> {
                    ORNetworkStatus.Data2G
                }

                TelephonyManager.NETWORK_TYPE_UMTS,
                TelephonyManager.NETWORK_TYPE_EVDO_0,
                TelephonyManager.NETWORK_TYPE_EVDO_A,
                TelephonyManager.NETWORK_TYPE_HSDPA,
                TelephonyManager.NETWORK_TYPE_HSUPA,
                TelephonyManager.NETWORK_TYPE_HSPA,
                TelephonyManager.NETWORK_TYPE_EVDO_B,
                TelephonyManager.NETWORK_TYPE_EHRPD,
                TelephonyManager.NETWORK_TYPE_HSPAP -> {
                    ORNetworkStatus.Data3G
                }

                TelephonyManager.NETWORK_TYPE_LTE -> {
                    ORNetworkStatus.Data4G
                }

                else -> {
                    ORNetworkStatus.Unknown
                }
            }
        }

    // MARK: RunningApi

    private var mRunningApis = mutableListOf<ORNetApiModel>()

    fun runningApis(): List<ORNetApiModel> {
        return mRunningApis
    }
    fun add(api: ORNetApiModel) {
        mRunningApis.add(api)
        Timber.i("[OR][NetApi][+]${mRunningApis.size} $api")
    }
    fun remove(api: ORNetApiModel) {
        if (mRunningApis.contains(api)) {
            mRunningApis.remove(api)
            Timber.i("[OR][NetApi][-]${mRunningApis.size} $api")
        }
    }
}