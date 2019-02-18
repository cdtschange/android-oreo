package com.cdts.demo.ui.listview.view

import android.support.v4.app.Fragment
import com.cdts.demo.schema.view.BaseSingleFragmentActivity

class ListTypeActivity: BaseSingleFragmentActivity() {
    override var fragment: Fragment = ListTypeFragment()

    var type: String? = null
    var title: String? = null

    override fun setupUI() {
        super.setupUI()
        (fragment as ListTypeFragment).let {
            it.type = type
            it.title = title
        }
    }
}