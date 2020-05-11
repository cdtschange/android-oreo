package com.cdts.demo.schema.view

import androidx.fragment.app.Fragment
import com.cdts.demo.R


abstract class BaseSingleFragmentActivity : BaseActivity() {
    protected abstract var fragment: Fragment

    override val layoutResID: Int = R.layout.activity_single_fragment

    override fun setupUI() {
        super.setupUI()

        if (supportFragmentManager.findFragmentById(R.id.container) == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()
        }
    }
}
