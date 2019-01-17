package com.cdts.oreo.ui.view.indicator

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface ORIndicatorProtocol {
    var showing: Boolean
    fun show(context: Context?, text: String?)
    fun hide()
}

class ORIndicator : ORIndicatorProtocol {

    override var showing: Boolean = false

    private var hud: KProgressHUD? = null

    override fun show(context: Context?, text: String?) {
        showing = true
        GlobalScope.launch(Dispatchers.Main) {
            if (context == null) {
                return@launch
            }
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
