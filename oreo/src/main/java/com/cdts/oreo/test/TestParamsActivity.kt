package com.cdts.oreo.test

import com.cdts.oreo.R
import com.cdts.oreo.ui.schema.view.ORBaseActivity
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import kotlinx.android.synthetic.main.test_fragment_list.*

class TestParamsActivity: ORBaseActivity() {

    override val titleBar: ORToolBar?
        get() = testToolBar
    override val layoutResID: Int = R.layout.test_activity_main
    override var indicator: ORIndicatorProtocol = ORIndicator()
    override var viewModel: ORBaseViewModel = ORBaseViewModel()

    var stringValue: String? = null
    var intValue: Int? = null
    var boolValue: Boolean? = null
    var doubleValue: Double? = null
    var listValue: List<String>? = null
}