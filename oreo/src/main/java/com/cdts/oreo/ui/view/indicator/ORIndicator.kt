package com.cdts.oreo.ui.view.indicator

import android.content.Context
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

interface ORIndicatorProtocol {
    var showing: Boolean
    fun show(context: Context?, text: String?)
    fun hide()
}

open class ORIndicator : ORIndicatorProtocol {

    override var showing: Boolean = false

    private var hud: KProgressHUD? = null
    private var toast: Toast? = null

    open fun showTip(context: Context?, text: String?, hideAfter: Int, completion: () -> Unit = {}) {
        if (context == null) {
            return
        }
        GlobalScope.launch(Dispatchers.Main) {
            toast?.cancel()
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            toast?.show()
            Timer("indicator").schedule(hideAfter.toLong()) {
                toast = null
                completion()
            }
        }
    }

    override fun show(context: Context?, text: String?) {
        if (context == null) {
            return
        }
        showing = true
        GlobalScope.launch(Dispatchers.Main) {
            var hud = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            if (text != null) {
                hud = hud.setLabel(text)
            }
            hud.show()
            this@ORIndicator.hud = hud
        }
    }

    override fun hide() {
        showing = false
        GlobalScope.launch(Dispatchers.Main) {
            hud?.let {
                it.dismiss()
                this@ORIndicator.hud = null
            }
        }
    }
}
