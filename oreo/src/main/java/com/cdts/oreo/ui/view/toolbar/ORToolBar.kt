package com.cdts.oreo.ui.view.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar

@Suppress("LeakingThis")
abstract class ORToolBar: LinearLayout {

    var view: View
    val inflater: LayoutInflater
    abstract val layoutResID: Int
    abstract var mToolbar: Toolbar

    constructor(context: Context): this(context,null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        inflater = LayoutInflater.from(context)
        view = inflater.inflate(layoutResID, this)
        setupUI()
    }

    open fun setupUI() {}
}