package com.cdts.demo.tab.view

import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.cdts.demo.R
import com.cdts.demo.schema.view.BaseActivity
import com.cdts.oreo.ui.view.navigation.ORTabNavHostFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layoutResID: Int = R.layout.activity_main

    override fun setupUI() {
        super.setupUI()

        NavigationUI.setupWithNavController(mainNav, (mainFrame as ORTabNavHostFragment).navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.mainFrame).navigateUp()
    }

}
