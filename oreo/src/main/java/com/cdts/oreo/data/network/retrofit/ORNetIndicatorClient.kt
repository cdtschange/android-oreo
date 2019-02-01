package com.cdts.oreo.data.network.retrofit

import android.content.Context
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import timber.log.Timber
import java.lang.ref.WeakReference

object ORNetIndicatorClient {

    data class IndicatorModel(val api: ORNetApiModel, val indicator: WeakReference<ORIndicatorProtocol?>, val context: WeakReference<Context?>, val text: String?)


    var indicators: MutableMap<String, IndicatorModel> = mutableMapOf()

    fun getIndicatorModel(identifier: String): IndicatorModel? {
        return indicators[identifier]
    }

    fun show(api: ORNetApiModel) {
        indicators[api.identifier]?.let { model ->
            model.indicator.get()?.let {
                it.show(model.context.get(), model.text)
            }
        }
    }
    fun hide(api: ORNetApiModel) {
        indicators[api.identifier]?.let { model ->
            model.indicator.get()?.hide()
        }
    }

    fun add(api: ORNetApiModel, indicator: ORIndicatorProtocol?, context: Context, text: String?) {
        Timber.d("[OR][NetIndicator][+] $api")
        indicators[api.identifier] = IndicatorModel(api, WeakReference(indicator), WeakReference(context), text)
    }
    fun remove(api: ORNetApiModel) {
        Timber.d("[OR][NetIndicator][-] $api")
        indicators.remove(api.identifier)
    }
}