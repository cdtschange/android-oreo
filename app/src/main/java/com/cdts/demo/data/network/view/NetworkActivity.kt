package com.cdts.demo.data.network.view

import androidx.fragment.app.Fragment
import com.cdts.demo.schema.view.BaseSingleFragmentActivity

class NetworkActivity: BaseSingleFragmentActivity() {
    override var fragment: Fragment = NetworkFragment()

    var type: String? = null
    var title: String? = null

    override fun setupUI() {
        super.setupUI()
        (fragment as NetworkFragment).let {
            it.type = type
            it.title = title
        }
    }
}