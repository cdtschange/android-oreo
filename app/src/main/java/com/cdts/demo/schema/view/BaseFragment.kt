package com.cdts.demo.schema.view

import com.cdts.demo.dagger.activity.DaggerFragmentComponent
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.ui.schema.view.ORBaseFragment
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import javax.inject.Inject

abstract class BaseFragment: ORBaseFragment() {

    override val titleBar: ORToolBar? = null

    @Inject
    override lateinit var indicator: ORIndicatorProtocol

    override fun setupDagger() {
        super.setupDagger()
        val fragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(MyApplication.applicationComponent)
                .fragmentModule(FragmentModule(this))
                .build()

        fragmentComponent.inject(this)
    }

}