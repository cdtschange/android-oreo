package com.cdts.oreo.ui.router

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cdts.oreo.data.model.SerializableMap
import timber.log.Timber

interface ORRouterProtocol {
    fun setParams(map: SerializableMap)
    fun routedBackEvent(from: String, result: Boolean, params: Map<String, Any>)
}

object ORRouter {

    var activityStack: MutableList<AppCompatActivity> = mutableListOf()

    fun push(activity: AppCompatActivity) {
        activityStack.add(activity)
    }
    fun pop(activity: AppCompatActivity) {
        activityStack.remove(activity)
    }

    fun topActivity(): AppCompatActivity {
        return activityStack.last()
    }

    fun routeToName(name: String, params: Map<String, Any> = mapOf(), requestCode: Int? = null, action: String? = null, uri: Uri? = null): Boolean {
        Timber.i("Route name: $name $params")
        topActivity().let { activity ->
            try {
                val actClass = Class.forName(name)
                val intent = Intent(action, uri, activity, actClass)
                val map = SerializableMap()
                map.map = params
                val bundle = Bundle()
                bundle.putSerializable("params", map)
                intent.putExtras(bundle)
                if (requestCode != null) {
                    activity.startActivityForResult(intent, requestCode)
                } else {
                    activity.startActivity(intent)
                }
                return true
            } catch (error: Exception) {
                Timber.e(error)
                return false
            }
        }
    }

    fun routeToUrl(name: String, url: String, params: Map<String, Any> = mapOf()): Boolean {
        Timber.i("Route url: $url")
        val newParams = mutableMapOf<String, Any>()
        newParams.putAll(params)
        newParams["url"] = url
        return routeToName(name, newParams)
    }
    fun routeToHtmlData(name: String, html: String, params: Map<String, Any> = mapOf()): Boolean {
        val newParams = mutableMapOf<String, Any>()
        newParams.putAll(params)
        newParams["htmlData"] = html
        return routeToName(name, newParams)
    }

    fun routeBack(className: String? = null, params: Map<String, Any> = mapOf(), result: Boolean = false): Boolean {
        Timber.i("Route back: $className $params")
        val name = if(className.isNullOrEmpty() && activityStack.size > 1) activityStack[activityStack.size - 2].javaClass.name else className
        val target: AppCompatActivity? = activityStack.lastOrNull { it.javaClass.name == name }
        if (target == null) {
            Timber.e("Route back failed")
            return false
        }
        for (i in activityStack.size - 1 downTo 0) {
            val av = activityStack[i]
            if (av.javaClass.name == name) {
                (target as? ORRouterProtocol)?.let {
                    val map = SerializableMap()
                    map.map = params
                    it.setParams(map)
                    target.routedBackEvent(topActivity().javaClass.simpleName, result, params)
                }
                break
            }
            av.finish()
        }

        return true
    }

}