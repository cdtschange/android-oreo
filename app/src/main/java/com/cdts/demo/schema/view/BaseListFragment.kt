package com.cdts.demo.schema.view

import android.view.View
import android.widget.ListView
import com.cdts.demo.R
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.dagger.activity.DaggerFragmentComponent
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.ui.schema.view.ORBaseListFragment
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import kotlinx.android.synthetic.main.fragment_list_with_toolbar.*
import javax.inject.Inject


abstract class BaseListFragment : ORBaseListFragment() {

    override val layoutResID: Int = R.layout.fragment_list_with_toolbar

    override val titleBar: ORToolBar?
        get() = demoToolBar

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

    override fun itemClickEvent(listView: ListView, view: View, position: Int, id: Long) {}
}