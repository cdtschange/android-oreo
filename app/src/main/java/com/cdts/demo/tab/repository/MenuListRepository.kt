package com.cdts.demo.tab.repository

import com.cdts.demo.data.cache.view.CacheActivity
import com.cdts.demo.data.device.view.DeviceInfoActivity
import com.cdts.demo.data.location.view.LocationActivity
import com.cdts.demo.data.network.view.NetworkActivity
import com.cdts.demo.router.routeToUrl
import com.cdts.demo.schema.repository.BaseRepository
import com.cdts.demo.tab.view.MenuListViewActivity
import com.cdts.demo.ui.animation.view.AnimationActivity
import com.cdts.demo.ui.animation.view.AnimationType
import com.cdts.demo.ui.indicatorview.view.IndicatorViewActivity
import com.cdts.demo.ui.listview.view.ListTypeActivity
import com.cdts.demo.ui.webview.view.SimpleWebViewActivity
import com.cdts.demo.ui.webview.view.WebBridgeViewActivity
import com.cdts.oreo.ui.router.ORRouter
import com.cdts.oreo.ui.schema.view.ORBaseListFragment
import io.reactivex.Observable
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MenuListRepository @Inject constructor(): BaseRepository() {


    fun fetchMenuItems(type: MenuType): Observable<List<MenuModel>> {
        when(type) {
            MenuType.UIComponent -> {
                val data = mutableListOf<MenuModel>()
                data.add(MenuModel("List View", "", MenuListViewActivity::class.java.name,
                    mapOf("type" to MenuType.ListView.name, "title" to "List View"), null))
                data.add(MenuModel("Web View", "", MenuListViewActivity::class.java.name,
                    mapOf("type" to MenuType.WebView.name, "title" to "Web View"), null))
                data.add(MenuModel("Indicator View", "", IndicatorViewActivity::class.java.name,
                    mapOf("title" to "Indicator View"), null))
                data.add(MenuModel("Animation", "", MenuListViewActivity::class.java.name,
                    mapOf("type" to MenuType.Animation.name, "title" to "Animation"), null))
                return Observable.just(data)
            }
            MenuType.Data -> {
                val data = mutableListOf<MenuModel>()
                data.add(MenuModel("Network", "", MenuListViewActivity::class.java.name,
                    mapOf("type" to MenuType.Network.name, "title" to "Network"), null))
                data.add(MenuModel("Cache", "", MenuListViewActivity::class.java.name,
                    mapOf("type" to MenuType.Cache.name, "title" to "Cache"), null))
                data.add(MenuModel("Device Info", "", DeviceInfoActivity::class.java.name,
                    mapOf("title" to "Device Info"), null))
                data.add(MenuModel("Location", "", LocationActivity::class.java.name,
                    mapOf("title" to "Location"), null))
                data.add(MenuModel("Crash", "", MenuListViewActivity::class.java.name,
                    mapOf("type" to MenuType.Crash.name, "title" to "Crash"), null))
                return Observable.just(data)
            }

            MenuType.ListView -> {
                val data = mutableListOf<MenuModel>()
                data.add(MenuModel("None Refresh List View", "List View without refresh header or load more footer", ListTypeActivity::class.java.name,
                    mapOf("type" to ORBaseListFragment.ListViewType.None.name, "title" to "None Refresh List View"), null))
                data.add(MenuModel("Refresh Only List View", "List View with only refresh header", ListTypeActivity::class.java.name,
                    mapOf("type" to ORBaseListFragment.ListViewType.RefreshOnly.name, "title" to "Refresh Only List View"), null))
                data.add(MenuModel("Load More Only List View", "List View with load more footer", ListTypeActivity::class.java.name,
                    mapOf("type" to ORBaseListFragment.ListViewType.LoadMoreOnly.name, "title" to "Load More Only List View"), null))
                data.add(MenuModel("Refresh & Load More List View", "List View with both refresh header and load more footer", ListTypeActivity::class.java.name,
                    mapOf("type" to ORBaseListFragment.ListViewType.Both.name, "title" to "Refresh & Load More List View"), null))
                return Observable.just(data)
            }
            MenuType.WebView -> {
                val data = mutableListOf<MenuModel>()
                data.add(MenuModel("Normal Web View Activity", "Visit a website with a BaseWebViewActivity", "", mapOf()) {
                    ORRouter.routeToUrl("https://www.baidu.com")
                })
                data.add(MenuModel("Web View Load From Html data", "Load Html data in WebViewActivity", "", mapOf()) {
                    ORRouter.routeToUrl(SimpleWebViewActivity::class.java.name, "")
                })
                data.add(MenuModel("Web View For Bridge", "Custom Bridge for navtive & js call each other", "", mapOf()) {
                    ORRouter.routeToUrl(WebBridgeViewActivity::class.java.name, "")
                })
                return Observable.just(data)
            }
            MenuType.Animation -> {
                val data = mutableListOf<MenuModel>()
                data.add(MenuModel("Frame Animation", "", AnimationActivity::class.java.name,
                    mapOf("type" to AnimationType.Frame.name, "title" to "Frame Animation"), null))
                data.add(MenuModel("Frame Accelerate Animation", "", AnimationActivity::class.java.name,
                    mapOf("type" to AnimationType.FrameAccelerate.name, "title" to "Frame Accelerate Animation"), null))
                data.add(MenuModel("Scale Animation", "", AnimationActivity::class.java.name,
                    mapOf("type" to AnimationType.Scale.name, "title" to "Scale Animation"), null))
                data.add(MenuModel("Rotation Animation", "", AnimationActivity::class.java.name,
                    mapOf("type" to AnimationType.Rotation.name, "title" to "Rotation Animation"), null))
                data.add(MenuModel("Alpha Animation", "", AnimationActivity::class.java.name,
                    mapOf("type" to AnimationType.Alpha.name, "title" to "Alpha Animation"), null))
                data.add(MenuModel("Color Animation", "", AnimationActivity::class.java.name,
                    mapOf("type" to AnimationType.Color.name, "title" to "Color Animation"), null))
                data.add(MenuModel("Combine Animation", "", AnimationActivity::class.java.name,
                    mapOf("type" to AnimationType.Combine.name, "title" to "Combine Animation"), null))
                return Observable.just(data)
            }

            MenuType.Network -> {
                val data = mutableListOf<MenuModel>()
                data.add(MenuModel("GET Request", "", NetworkActivity::class.java.name, mapOf("title" to "GET Request", "type" to "Get"), null))
                data.add(MenuModel("POST Request", "", NetworkActivity::class.java.name, mapOf("title" to "POST Request", "type" to "Post"), null))
                data.add(MenuModel("PUT Request", "", NetworkActivity::class.java.name, mapOf("title" to "PUT Request", "type" to "Put"), null))
                data.add(MenuModel("DELETE Request", "", NetworkActivity::class.java.name, mapOf("title" to "DELETE Request", "type" to "Delete"), null))
                data.add(MenuModel("Upload Multipart Data", "", NetworkActivity::class.java.name, mapOf("title" to "Upload Multipart Data", "type" to "MultipartUpload"), null))
                return Observable.just(data)
            }
            MenuType.Cache -> {
                val data = mutableListOf<MenuModel>()
                data.add(MenuModel("Cache in temporary file", "", CacheActivity::class.java.name, mapOf("title" to "Cache in temporary file", "type" to "Cache"), null))
                data.add(MenuModel("Cache in disk", "", CacheActivity::class.java.name, mapOf("title" to "Cache in disk", "type" to "Disk"), null))
                data.add(MenuModel("Cache in SharedPreferences", "", CacheActivity::class.java.name, mapOf("title" to "Cache in SharedPreferences", "type" to "SharedPreferences"), null))
                data.add(MenuModel("Cache with LiveData", "", CacheActivity::class.java.name, mapOf("title" to "Cache with LiveData", "type" to "LiveData"), null))
                return Observable.just(data)
            }
            MenuType.Crash -> {
                val data = mutableListOf<MenuModel>()
                data.add(MenuModel("NullPointerException", "空指针异常", "", mapOf()) {
                    val a: String? = null
                    print(a!!)
                })
                data.add(MenuModel("IndexOutOfBoundsException", "越界异常", "", mapOf()) {
                    val a = listOf(1, 2, 3)
                    print(a[3])
                })
                data.add(MenuModel("IllegalArgumentException", "参数异常", "", mapOf()) {
                    throw IllegalArgumentException()
                })
                data.add(MenuModel("IllegalStateException", "状态异常", "", mapOf()) {
                    throw IllegalStateException()
                })
                data.add(MenuModel("IllegalAccessException", "权限异常", "", mapOf()) {
                    throw IllegalAccessException()
                })
                data.add(MenuModel("ClassNotFoundException", "类不存在异常", "", mapOf()) {
                    throw ClassNotFoundException()
                })
                return Observable.just(data)
            }
            else -> return Observable.just(listOf())
        }
    }
}

enum class MenuType {
    None, UIComponent, Data,
    ListView, WebView, Animation,
    Network, Cache, Crash
}

data class MenuModel(val title: String, val detail: String, val url: String, val params: Map<String, Any>, val callback: (() -> Unit)?)