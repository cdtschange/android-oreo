package com.cdts.oreo.extension

import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.test.MainTestActivity
import com.cdts.oreo.ui.router.ORRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewExTests: BaseTestCase() {
    @get:Rule
    var activityRule: ActivityTestRule<MainTestActivity> = ActivityTestRule(MainTestActivity::class.java,true,true)


    @Test
    fun testView() {
        val activity = ORRouter.topActivity() as? MainTestActivity
        val linearLayout = LinearLayout(activity)
        View(InstrumentationRegistry.getContext()).getActivity()
        assert(linearLayout.getActivity() == activity)
        val view = View(ContextWrapper(activity!!))
        linearLayout.addView(view)
        assert(view.getActivity() == activity)
        var x = 1
        GlobalScope.launch(Dispatchers.Main) {
            view.addClickAction {
                x = 2
                signal()
            }
            view.callOnClick()
        }
        await()
        assert(x == 2)

    }
}