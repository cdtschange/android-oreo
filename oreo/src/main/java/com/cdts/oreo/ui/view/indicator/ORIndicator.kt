package com.cdts.oreo.ui.view.indicator

import android.content.Context
import android.support.annotation.DrawableRes
import android.view.Gravity
import android.widget.ImageView
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule


interface ORIndicatorProtocol {
    var showing: Boolean
    fun show(context: Context?, text: String?, detailText: String?)
    fun hide()
}

open class ORIndicator : ORIndicatorProtocol {

    override var showing: Boolean = false

    private var hud: KProgressHUD? = null
    private var toast: Toast? = null

    open fun createHUD(context: Context?): KProgressHUD? {
        if (context == null) {
            return null
        }
        return KProgressHUD.create(context)
    }

    open fun showTip(context: Context?, text: String?, hideAfter: Int, completion: () -> Unit = {}) {
        showTip(context, text, null, null, true, hideAfter, completion)
    }
    fun showTip(context: Context?, text: String?, detailText: String?, @DrawableRes image: Int?, center: Boolean, hideAfter: Int, completion: () -> Unit = {}) {
        if (context == null) {
            return
        }
        if (image != null) {
            GlobalScope.launch(Dispatchers.Main) {
                var hud = createHUD(context)!!
                val imageView = ImageView(context)
                imageView.setImageResource(image)
                hud = hud.setCustomView(imageView)
                if (text != null) {
                    hud = hud.setLabel(text)
                }
                if (detailText != null) {
                    hud = hud.setDetailsLabel(detailText)
                }
                hud.show()
                Timer("indicator").schedule(hideAfter.toLong() * 1000) {
                    hud.dismiss()
                    completion()
                }
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                toast?.cancel()
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
                if (center) {
                    toast?.setGravity(Gravity.CENTER, 0, 0)
                }
                toast?.show()
                Timer("indicator").schedule(hideAfter.toLong() * 1000) {
                    toast = null
                    completion()
                }
            }
        }
    }

    override fun show(context: Context?, text: String?, detailText: String?) {
        if (context == null) {
            return
        }
        showing = true
        GlobalScope.launch(Dispatchers.Main) {
            var hud = createHUD(context)!!.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            if (text != null) {
                hud = hud.setLabel(text)
            }
            if (detailText != null) {
                hud = hud.setDetailsLabel(detailText)
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

    fun showProgress(context: Context?, text: String?, detailText: String?, cancelable: Boolean, cancelHandler: (() -> Unit)?, type: ORIndicatorType) {
        if (context == null) {
            return
        }
        showing = true
        GlobalScope.launch(Dispatchers.Main) {
            var hud = createHUD(context)!!.setStyle(type.toKPStyle())
            if (text != null) {
                hud = hud.setLabel(text)
            }
            if (detailText != null) {
                hud = hud.setDetailsLabel(detailText)
            }
            if (cancelable) {
                hud = hud.setCancellable(cancelable)
                hud = hud.setCancellable {
                    cancelHandler?.invoke()
                }
            }
            hud = hud.setMaxProgress(100)
            hud.show()
            this@ORIndicator.hud = hud
        }
    }
    fun changeProgress(text: String?, detailText: String?, progress: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            if (text != null) {
                hud?.setLabel(text)
            }
            if (detailText != null) {
                hud?.setDetailsLabel(detailText)
            }
            this@ORIndicator.hud?.setProgress(progress)
        }
    }
}

enum class ORIndicatorType {
    SpinDeterminate, AnnularDeterminate, BarDeterminate, PieDeterminate;

    fun toKPStyle(): KProgressHUD.Style {
        return when(this) {
            SpinDeterminate -> KProgressHUD.Style.SPIN_INDETERMINATE
            AnnularDeterminate -> KProgressHUD.Style.ANNULAR_DETERMINATE
            BarDeterminate -> KProgressHUD.Style.BAR_DETERMINATE
            PieDeterminate -> KProgressHUD.Style.PIE_DETERMINATE
        }
    }
}
