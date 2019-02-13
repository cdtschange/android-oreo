package com.cdts.oreo.data.network

import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.data.network.retrofit.ORNetClient
import org.junit.Test
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.cdts.oreo.test.MainTestActivity
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ORNetClientTests: BaseTestCase() {
    @get:Rule
    var activityRule: ActivityTestRule<MainTestActivity> = ActivityTestRule(MainTestActivity::class.java,true,true)

    @Test
    fun testNetClient() {
        ORNetClient.getNetworkStatus { status ->
            print(status.reachableCellular)
            print(status.reachableWifi)
            signal()
        }

        await()
    }
}