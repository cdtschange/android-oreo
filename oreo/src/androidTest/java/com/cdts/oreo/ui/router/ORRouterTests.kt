package com.cdts.oreo.ui.router

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.app.Fragment
import android.view.View
import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.R
import com.cdts.oreo.data.network.ORError
import com.cdts.oreo.test.*
import com.cdts.oreo.ui.router.ORRouter
import com.cdts.oreo.ui.schema.view.ORBaseActivity
import com.cdts.oreo.ui.view.indicator.ORIndicator
import kotlinx.android.synthetic.main.test_activity_single_fragment.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.security.InvalidParameterException

@RunWith(AndroidJUnit4::class)
class ORRouterTests: BaseTestCase() {
    @get:Rule
    var activityRule: ActivityTestRule<MainTestActivity> = ActivityTestRule(MainTestActivity::class.java,true,true)

    @Test
    fun testActivity() {

        val params = mutableMapOf<String, Any>()
        params["stringValue"] = "abc"
        params["intValue"] = 123
        params["boolValue"] = true
        params["listValue"] = listOf("a", "b")
        params["doubleValue"] = 1.2
        params["none"] = "none"


        GlobalScope.launch(Dispatchers.Main) {
            val activity = ORRouter.topActivity() as MainTestActivity
            print(activity.indicator)
            print(activity.viewModel)
            delay(4000)
            activity.selectIndex(1)
            delay(2000)
            activity.selectIndex(0)
            delay(4000)
            assert(!ORRouter.routeToName("com.cdts.oreo.test.TestParamsActivity", params + mapOf<String, Any>("model" to TestModel("abc"))))
            assert(ORRouter.routeToName("com.cdts.oreo.test.TestParamsActivity", params))
            delay(2000)
            val activity2 = ORRouter.topActivity() as TestParamsActivity
            assert(activity2.stringValue == "abc")
            assert(activity2.intValue == 123)
            assert(activity2.boolValue == true)
            assert(activity2.listValue == listOf("a", "b"))
            assert(activity2.doubleValue == 1.2)
            print(activity2.indicator)
            print(activity2.viewModel)
            activity2.loadData()
            activity2.showIndicator()
            activity2.hideIndicator()
            activity2.showTip(ORError(0, "")) {}
            activity2.showTip(InvalidParameterException("")) {}
            activity2.showTip("") {}
            activity2.showIndicatorObserver().subscribe()
            activity2.setupLeftBackAction(View(activity2)) {}
            assert(activity2.topMostActivity() == ORRouter.topActivity())
            val ob = TestOnResultObserverListener()
            activity2.addResultObserver(ob)
            activity2.removeResultObserver(ob)
            delay(3000)
            activity2.onBackPressed()
            delay(1000)
            assert(ORRouter.routeToUrl("com.cdts.oreo.test.TestWebViewActivity", "https://www.baidu.com"))
            delay(4000)
            val activity3 = ORRouter.topActivity() as TestWebViewActivity
            val fragment3 = activity3.fragment as TestWebViewFragment
            print(fragment3.indicator)
            print(fragment3.viewModel)
            fragment3.showIndicator()
            fragment3.hideIndicator()
            fragment3.showTip(ORError(0, "")) {}
            fragment3.showTip(InvalidParameterException("")) {}
            fragment3.showTip("") {}
            fragment3.showIndicatorObserver().subscribe()
            fragment3.invokeWebViewMethod("test", "")

            assert(ORRouter.routeBack())
            delay(1000)
            assert(ORRouter.routeToHtmlData("com.cdts.oreo.test.TestWebViewActivity", "<raw>\n" +
                    "    <head>\n" +
                    "        <meta content=\"text/raw; charset=utf-8\" http-equiv=\"content-type\"/>\n" +
                    "        <title>\n" +
                    "            Simple Html\n" +
                    "        </title>\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "        <h1>Hello World!</h1>\n" +
                    "    </body>\n" +
                    "</raw>"))
            delay(2000)
            assert(ORRouter.routeBack())
            delay(1000)

            signal()
        }

        await()

    }

    data class TestModel(val name: String)


    class TestOnResultObserverListener: ORBaseActivity.OnResultObserverListener {
        override fun invoke(requestCode: Int, resultCode: Int, data: Intent?) {}
    }
}