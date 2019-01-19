package com.cdts.oreo.ui.router

import android.support.v7.app.AppCompatActivity

object ORRouter {

    var activityStack: MutableList<AppCompatActivity> = mutableListOf()

    fun push(activity: AppCompatActivity) {
        activityStack.add(activity)
    }
    fun pop(activity: AppCompatActivity) {
        activityStack.remove(activity)
    }

    fun topActivity(): AppCompatActivity {
        return activityStack.last()
    }
}