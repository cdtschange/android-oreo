package com.cdts.oreo.data.network

import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.data.network.retrofit.ORNetClient
import org.junit.Test
import android.telephony.TelephonyManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.cdts.oreo.data.network.retrofit.ORNetworkStatus
import com.cdts.oreo.test.MainTestActivity
import com.cdts.oreo.ui.router.ORRouter
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ORNetClientTests: BaseTestCase() {
    @get:Rule
    var activityRule: ActivityTestRule<MainTestActivity> = ActivityTestRule(MainTestActivity::class.java,true,true)

    @Test
    fun testNetClient() {
        val activity = ORRouter.topActivity() as? MainTestActivity
        print(activity?.titleBar)
        print(activity?.layoutResID)
        ORNetClient.getNetworkStatus { status ->
            print(status.reachableCellular)
            print(status.reachableWifi)
            signal()
        }

        await()
    }

    @Test
    fun testStatus() {
        print(ORNetClient.getNetworkClass())
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_GPRS) == ORNetworkStatus.Data2G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_EDGE) == ORNetworkStatus.Data2G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_CDMA) == ORNetworkStatus.Data2G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_1xRTT) == ORNetworkStatus.Data2G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_IDEN) == ORNetworkStatus.Data2G)

        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_UMTS) == ORNetworkStatus.Data3G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_EVDO_0) == ORNetworkStatus.Data3G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_EVDO_A) == ORNetworkStatus.Data3G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_HSDPA) == ORNetworkStatus.Data3G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_HSUPA) == ORNetworkStatus.Data3G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_HSPA) == ORNetworkStatus.Data3G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_EVDO_B) == ORNetworkStatus.Data3G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_EHRPD) == ORNetworkStatus.Data3G)
        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_HSPAP) == ORNetworkStatus.Data3G)

        assert(ORNetClient.translateStatus(TelephonyManager.NETWORK_TYPE_LTE) == ORNetworkStatus.Data4G)

        assert(ORNetClient.translateStatus(null) == ORNetworkStatus.Unknown)
    }
}