package com.cdts.demo

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.InstrumentationRegistry
import com.cdts.demo.ui.tab.view.MainActivity
import org.junit.After
import org.junit.Before
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

open class BaseTestCase {

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    lateinit var monitor: Instrumentation.ActivityMonitor
    lateinit var currentActivity: Activity

//    @Before
    fun before() {
        monitor = InstrumentationRegistry.getInstrumentation().addMonitor(MainActivity::class.java.name, null, false)

        val intent = Intent(Intent.ACTION_MAIN)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        intent.setClassName(InstrumentationRegistry.getTargetContext(), MainActivity::class.java.name)
        InstrumentationRegistry.getInstrumentation().startActivitySync(intent)

        currentActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(monitor, 5)!!
    }
//    @After
    fun after() {
        InstrumentationRegistry.getInstrumentation().removeMonitor(monitor)
    }

    fun signal() {
        lock.withLock {
            condition.signal()
        }
    }

    fun await() {
        lock.withLock {
            condition.await()
        }
    }
}