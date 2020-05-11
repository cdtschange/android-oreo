package com.cdts.demo.ui.listview.view

import androidx.fragment.app.Fragment
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