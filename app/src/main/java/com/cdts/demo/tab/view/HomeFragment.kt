package com.cdts.demo.tab.view

import com.cdts.demo.R
import com.cdts.demo.schema.view.BaseFragment
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment() {
    override val layoutResID: Int = R.layout.fragment_home

    override val titleBar: ORToolBar?
        get() = demoToolBar

    override fun setupUI() {
        super.setupUI()
        titleBar?.mToolbar?.navigationIcon = null
    }
}