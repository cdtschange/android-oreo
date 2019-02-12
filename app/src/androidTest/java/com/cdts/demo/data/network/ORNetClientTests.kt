package com.cdts.demo.data.network

import com.cdts.demo.BaseTestCase
import com.cdts.oreo.data.network.retrofit.ORNetClient
import org.junit.Test


class ORNetClientTests: BaseTestCase() {

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