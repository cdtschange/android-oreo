package com.cdts.oreo.ui.view.actionsheet

import android.Manifest
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.test.MainTestActivity
import com.cdts.oreo.ui.router.ORRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ORPhtoActionSheetTests: BaseTestCase() {
    @get:Rule
    var activityRule: ActivityTestRule<MainTestActivity> = ActivityTestRule(MainTestActivity::class.java,true,false)

    @get:Rule
    val permissionRuleCamera = GrantPermissionRule.grant(Manifest.permission.CAMERA)
    @get:Rule
    val permissionRuleWriteExternalStorage = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    @Test
    fun testActionSheet() {

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        activityRule.launchActivity(Intent(context, MainTestActivity::class.java))

        GlobalScope.launch(Dispatchers.Main) {
            val dialog = ORPhotoActionSheet.showPhoto(ORRouter.topActivity()) {_,_,_ ->}
            delay(2000)
            dialog.dismiss()
            delay(1000)
            ORPhotoActionSheet.showCamera(ORRouter.topActivity()) {_,_,_ ->}
            delay(1000)
            signal()
        }

        await()

        val intent = Intent(context, MainTestActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        ORRouter.topActivity().startActivity(intent)

        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            ORPhotoActionSheet.showFile(ORRouter.topActivity()) {_,_,_ ->}
            delay(1000)
            signal()
        }

        await()

        ORRouter.topActivity().startActivity(intent)

        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            ORPhotoActionSheet.showLibrary(ORRouter.topActivity()) {_,_,_ ->}
            delay(1000)
            signal()
        }

        await()

        val intent2 = Intent(context, MainTestActivity::class.java)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        ORRouter.topActivity().startActivity(intent2)

        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            signal()
        }

        await()
    }
}