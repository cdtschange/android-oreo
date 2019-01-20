package com.cdts.demo.schema.view

import com.cdts.demo.R
import com.cdts.demo.dagger.activity.DaggerActivityComponent
import com.cdts.demo.dagger.activity.module.ActivityModule
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.ui.schema.view.ORBaseActivity
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import javax.inject.Inject

abstract class BaseActivity: ORBaseActivity() {

    override var titleBar: ORToolBar? = null
    open var statusBarColor: Int = R.color.colorPrimary
    open var isFitSystemWindows = true

    @Inject
    override lateinit var indicator: ORIndicatorProtocol


    override fun setupDagger() {
        super.setupDagger()
        val activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.applicationComponent)
                .activityModule(ActivityModule(this))
                .build()

        activityComponent.inject(this)
    }


    open fun setupStatusBar() {
        setupStatusBar(statusBarColor, isFitSystemWindows)
    }
}