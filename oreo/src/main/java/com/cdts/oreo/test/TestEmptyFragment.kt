package com.cdts.oreo.test

import com.cdts.oreo.R
import com.cdts.oreo.ui.schema.view.ORBaseFragment
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import kotlinx.android.synthetic.main.test_fragment_empty.*

class TestEmptyFragment: ORBaseFragment() {
    override val layoutResID: Int = R.layout.test_fragment_empty
    override var indicator: ORIndicatorProtocol = ORIndicator()
    override var viewModel: ORBaseViewModel = ORBaseViewModel()

    override val titleBar: ORToolBar?
        get() = testToolBar
}