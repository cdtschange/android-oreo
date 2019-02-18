package com.cdts.demo.tab.view

import android.support.v4.app.Fragment
import com.cdts.demo.schema.view.BaseSingleFragmentActivity
import com.cdts.demo.tab.repository.MenuType

class MenuListViewActivity: BaseSingleFragmentActivity() {
    override var fragment: Fragment = MenuListViewFragment()

    var type: String = MenuType.None.name
    var title: String? = null

    override fun setupUI() {
        super.setupUI()
        (fragment as MenuListViewFragment).let {
            it.type = type
            it.title = title
        }
    }
}