package com.cdts.oreo.test

import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.cdts.oreo.R
import com.cdts.oreo.ui.schema.view.ORBaseActivity
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.navigation.ORTabNavHostFragment
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import kotlinx.android.synthetic.main.test_activity_main.*


class MainTestActivity : ORBaseActivity() {

    override var titleBar: ORToolBar? = null
    override var layoutResID: Int = R.layout.test_activity_main
    override var indicator: ORIndicatorProtocol = ORIndicator()
    override var viewModel: ORBaseViewModel = ORBaseViewModel()

    override fun setupUI() {
        super.setupUI()
        NavigationUI.setupWithNavController(mainNav, (mainFrame as ORTabNavHostFragment).navController)

        setupStatusBar(R.color.colorPrimary, true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.mainFrame).navigateUp()
    }

    fun selectIndex(index: Int) {
        if (index == 0) {
            mainNav.selectedItemId = R.id.navigation_list
        } else {
            mainNav.selectedItemId = R.id.navigation_empty
        }

    }
}
