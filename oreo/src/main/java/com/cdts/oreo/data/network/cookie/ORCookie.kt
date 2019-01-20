package com.cdts.oreo.data.network.cookie

import timber.log.Timber

/**
 * Created by cdts on 14/02/2017.
 */

object ORCookie {
    object Constant {
        const val COOKIE_SPLIT = "&&"
    }
    private var cookieString: String = ""

    var cookieArray: Array<String>
        get() = cookieString.split(Constant.COOKIE_SPLIT).dropLastWhile { it.isEmpty() }.toTypedArray()
        set(value) {
            if (value.isEmpty()) {
                return
            }
            val list = arrayListOf<String>()
            if (!cookieString.isEmpty()) {
                val arr = cookieString.split(Constant.COOKIE_SPLIT).dropLastWhile { it.isEmpty() }.toTypedArray()
                list.addAll(arr)
            }
            for (cookie in value) {
                if (cookie.contains("=")) {
                    val key = cookie.substring(0, cookie.indexOf("="))
                    var modify = false
                    for (oldCookie in list) {
                        if (oldCookie.contains("=")) {
                            val oldKey = oldCookie.substring(0, oldCookie.indexOf("="))
                            if (key == oldKey) {
                                list.remove(oldCookie)
                                list.add(cookie)
                                modify = true
                                break
                            }
                        }
                    }
                    if (!modify) {
                        list.add(cookie)
                    }
                } else if (!cookie.isEmpty()) {
                    list.add(cookie)
                }
            }
            val result = list.joinToString(Constant.COOKIE_SPLIT)
            Timber.i("Set Cookies: $result")
            cookieString = result
        }

    fun clear() {
        cookieString = ""
    }
}
