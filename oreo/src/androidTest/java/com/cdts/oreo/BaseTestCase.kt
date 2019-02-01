package com.cdts.oreo

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

open class BaseTestCase {

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

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