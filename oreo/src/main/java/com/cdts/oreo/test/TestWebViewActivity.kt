package com.cdts.oreo.test

import androidx.fragment.app.Fragment
import com.cdts.oreo.R
import com.cdts.oreo.ui.schema.view.ORBaseActivity
import com.cdts.oreo.ui.schema.view.webview.ORBaseWebViewFragment
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.toolbar.ORToolBar


class TestWebViewActivity : ORBaseActivity() {

    var url: String? = null
    var htmlData: String? = null

    override val titleBar: ORToolBar? = null
    override var indicator: ORIndicatorProtocol = ORIndicator()
    override var viewModel: ORBaseViewModel = ORBaseViewModel()
    var fragment: Fragment = TestWebViewFragment()

    override val layoutResID: Int = R.layout.test_activity_single_fragment

    override fun setupUI() {
        super.setupUI()

        if (supportFragmentManager.findFragmentById(R.id.container) == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()
        }

        (fragment as ORBaseWebViewFragment)?.let {
            it.url = url
            it.htmlData = htmlData
        }
    }
}
