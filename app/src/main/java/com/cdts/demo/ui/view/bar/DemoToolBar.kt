package com.cdts.demo.ui.view.bar

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.cdts.demo.R
import com.cdts.oreo.ui.router.ORRouter
import com.cdts.oreo.ui.schema.view.Bind
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import kotlinx.android.synthetic.main.toolbar_title.view.*

class DemoToolBar : ORToolBar {
    constructor(context: Context): this(context,null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override val layoutResID: Int
        get() = R.layout.toolbar_title

    override var mToolbar: Toolbar by Bind(R.id.toolbar)

    var centerText: String = ""
        set(value) {
            field = value
            toolbarTitle.text = value
        }

    var showBackButton: Boolean = true
        set(value) {
            field = value
            if (value) {
                mToolbar.navigationIcon = ContextCompat.getDrawable(ORRouter.topActivity(), R.drawable.ic_nav_back)
            } else {
                mToolbar.navigationIcon = null
            }
        }

    var barBackgroundColor: Int = R.color.colorPrimary
        set(value) {
            field = value
            mToolbar.setBackgroundColor(value)
        }

    var barTintColor: Int = R.color.colorWhite
        set(value) {
            field = value
            toolbarTitle.setTextColor(value)
            leftTextView.setTextColor(value)
            rightTextView.setTextColor(value)
            toolbar.setTitleTextColor(value)
        }

    fun settingBackAction(action: () -> Unit) {
        settingBackAction(View.OnClickListener {
            action()
        })
    }
    fun settingBackAction(action: View.OnClickListener) {
        mToolbar.setNavigationOnClickListener {
            action.onClick(it)
        }
    }

    fun settingLeftButton(text: String, action: () -> Unit) {
        settingLeftButton(text, View.OnClickListener {
            action()
        })
    }
    fun settingLeftButton(text: String?, action: View.OnClickListener?) {
        mToolbar.leftTextView.visibility = View.VISIBLE
        mToolbar.leftTextView.text = text
        mToolbar.leftTextView.setOnClickListener {
            action?.onClick(it)
        }
    }

    fun settingRightButton(text: String, action: () -> Unit) {
        settingRightButton(text, View.OnClickListener {
            action()
        })
    }
    fun settingRightButton(text: String?, action: View.OnClickListener?) {
        mToolbar.rightTextView.visibility = View.VISIBLE
        mToolbar.rightTextView.text = text
        mToolbar.rightTextView.setOnClickListener {
            action?.onClick(it)
        }
    }

    companion object {
        @BindingAdapter("centerText")
        @JvmStatic
        fun layoutCenterText(view: DemoToolBar,
                                     centerText: String) {
            view.centerText = centerText
        }

        @BindingAdapter("showBackButton")
        @JvmStatic
        fun layoutShowBackButton(view: DemoToolBar,
                             showBackButton: Boolean) {
            view.showBackButton = showBackButton
        }

        @BindingAdapter("barBackgroundColor")
        @JvmStatic
        fun layoutBarBackgroundColor(view: DemoToolBar,
                                     @ColorInt barBackgroundColor: Int) {
            view.barBackgroundColor = barBackgroundColor
        }

        @BindingAdapter("barTintColor")
        @JvmStatic
        fun layoutBarTintColor(view: DemoToolBar,
                                     @ColorInt barTintColor: Int) {
            view.barTintColor = barTintColor
        }
        @BindingAdapter("leftText", "leftAction", requireAll = false)
        @JvmStatic
        fun layoutLeftButton(view: DemoToolBar,
                             text: String?, listener: View.OnClickListener?) {
            view.settingLeftButton(text, listener)
        }
        @BindingAdapter("rightText", "rightAction", requireAll = false)
        @JvmStatic
        fun layoutRightButton(view: DemoToolBar,
                             text: String?, listener: View.OnClickListener?) {
            view.settingRightButton(text, listener)
        }
        @BindingAdapter("backAction")
        @JvmStatic
        fun layoutBackButtonAction(view: DemoToolBar,
                                   listener: View.OnClickListener) {
            view.settingBackAction(listener)
        }


    }
}

