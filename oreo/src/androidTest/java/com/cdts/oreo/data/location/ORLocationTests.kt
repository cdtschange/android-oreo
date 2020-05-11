package com.cdts.oreo.data.location

import android.Manifest
import android.view.MotionEvent
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.test.MainTestActivity
import org.junit.Rule
import org.junit.Test

class ORLocationTests: BaseTestCase() {
    @get:Rule
    var activityRule: ActivityTestRule<MainTestActivity> = ActivityTestRule(MainTestActivity::class.java,true,true)
    @get:Rule
    val permissionRuleACCESSCOARSELOCATION = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION)
    @get:Rule
    val permissionRuleACCESSFINELOCATION = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION)

    @Test
    fun testLocation() {
        ORLocation.requestLocation { _, _, _ ->
            signal()
        }
        await()
        ORLocation.transferToPlace(0.0, 0.0)
    }
}