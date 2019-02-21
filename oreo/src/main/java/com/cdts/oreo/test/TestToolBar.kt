package com.cdts.oreo.test

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import com.cdts.oreo.R
import com.cdts.oreo.ui.schema.view.Bind
import com.cdts.oreo.ui.view.toolbar.ORToolBar


class TestToolBar : ORToolBar {
    constructor(context: Context): this(context,null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override val layoutResID: Int
        get() = R.layout.test_toolbar_title

    override var mToolbar: Toolbar by Bind(R.id.toolbar)
}

