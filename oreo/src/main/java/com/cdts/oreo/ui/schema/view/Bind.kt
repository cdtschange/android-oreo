package com.cdts.oreo.ui.schema.view

import android.app.Activity
import android.app.Dialog
import android.app.Fragment
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class Bind<T, V : View>(private val id: Int, private val rootView: View? = null) : ReadOnlyProperty<T, V> {
    var lazyValue: V? = null

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (lazyValue != null) return lazyValue!!
        val injectView = if (rootView != null) {
            rootView.findViewById(id) as V
        } else when (thisRef) {
            is Activity -> thisRef.findViewById(id) as V? ?: throw BindResourceError(id, "unknown rootView in Activity")
            is android.support.v4.app.Fragment -> thisRef.view!!.findViewById(id) as V? ?: throw BindResourceError(id, "unknown rootView in V4 Fragment")
            is Fragment -> thisRef.view?.findViewById(id) as V? ?: throw BindResourceError(id, "unknown rootView in Fragment")
//            is RecyclerView.ViewHolder -> thisRef.itemView.findViewById(id) as V? ?: throw BindResourceError(id, "can't find view of $comment")
            is Dialog -> thisRef.findViewById(id) as V? ?: throw BindResourceError(id, "unknown rootView in Dialog")
            is View -> thisRef.findViewById(id) as V? ?: throw BindResourceError(id, "unknown rootView in View")
            else -> throw BindResourceError(id, "unknown rootView while binding")
        }
        lazyValue = injectView
        return injectView
    }

    operator fun setValue(thisRef: T, property: KProperty<*>, value: V) {
    }
}

class BindResourceError(id: Int, msg: String) : Exception("cant'bind resource $id for $msg ")