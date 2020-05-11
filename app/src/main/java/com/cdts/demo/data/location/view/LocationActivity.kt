package com.cdts.demo.data.location.view

import androidx.fragment.app.Fragment
import com.cdts.demo.schema.view.BaseSingleFragmentActivity

class LocationActivity: BaseSingleFragmentActivity() {
    override var fragment: Fragment = LocationFragment()

    var title: String? = null

    override fun setupUI() {
        super.setupUI()
        (fragment as LocationFragment).let {
            it.title = title
        }
    }
}