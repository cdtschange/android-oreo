package com.cdts.demo.ui.tab.view

import com.cdts.demo.R
import com.cdts.demo.schema.view.BaseFragment
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment() {
    override var layoutResID: Int = R.layout.fragment_home

    override var titleBar: ORToolBar? = null
        get() = demoToolBar

    override fun setupUI() {
        super.setupUI()
        titleBar?.mToolbar?.navigationIcon = null
    }
}