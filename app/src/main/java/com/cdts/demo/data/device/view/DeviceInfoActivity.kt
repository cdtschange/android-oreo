package com.cdts.demo.data.device.view

import androidx.fragment.app.Fragment
import com.cdts.demo.schema.view.BaseSingleFragmentActivity

class DeviceInfoActivity: BaseSingleFragmentActivity() {
    override var fragment: Fragment = DeviceInfoFragment()

    var title: String? = null

    override fun setupUI() {
        super.setupUI()
        (fragment as DeviceInfoFragment).let {
            it.title = title
        }
    }
}