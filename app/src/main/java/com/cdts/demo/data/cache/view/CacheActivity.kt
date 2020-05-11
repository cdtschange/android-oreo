package com.cdts.demo.data.cache.view

import androidx.fragment.app.Fragment
import com.cdts.demo.schema.view.BaseSingleFragmentActivity

class CacheActivity: BaseSingleFragmentActivity() {
    override var fragment: Fragment = CacheFragment()

    var type: String? = null
    var title: String? = null

    override fun setupUI() {
        super.setupUI()
        (fragment as CacheFragment).let {
            it.type = type
            it.title = title
        }
    }
}