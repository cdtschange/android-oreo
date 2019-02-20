package com.cdts.demo.tab.repository

import com.cdts.demo.router.routeToUrl
import com.cdts.demo.schema.repository.BaseRepository
import com.cdts.demo.tab.view.MenuListViewActivity
import com.cdts.demo.ui.indicatorview.view.IndicatorViewActivity
import com.cdts.demo.ui.listview.view.ListTypeActivity
import com.cdts.demo.ui.webview.view.SimpleWebViewActivity
import com.cdts.demo.ui.webview.view.WebBridgeViewActivity
import com.cdts.oreo.ui.router.ORRouter
import com.cdts.oreo.ui.schema.view.ORBaseListFragment
import io.reactivex.Observable
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
                return Observable.just(data)
            }
            MenuType.Data -> {
                val data = mutableListOf<MenuModel>()
//                data.add(ListItem("Location", "(${AKLocation.locationModel?.latitude ?: 0.0}, ${AKLocation.locationModel?.longitude ?: 0.0})", LocationActivity::class.java.name))
//                data.add(ListItem("Device", "Device Info", DeviceInfoActivity::class.java.name))
//                data.add(ListItem("Permission", "Permission", PermissionActivity::class.java.name))
//                data.add(ListItem("Cache", "Local Cache", CacheActivity::class.java.name))
//                data.add(ListItem("Network", "Retrofit", NetworkActivity::class.java.name))
//                data.add(ListItem("Jetpack", "JetPack", JetpackActivity::class.java.name))
//                data.add(ListItem("Installed App", "PackageManager", InstalledAppActivity::class.java.name))
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
                data.add(MenuModel("Refersh & Load More List View", "List View with both refresh header and load more footer", ListTypeActivity::class.java.name,
                    mapOf("type" to ORBaseListFragment.ListViewType.Both.name, "title" to "Refersh & Load More List View"), null))
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
            else -> return Observable.just(listOf())
        }
    }
}

enum class MenuType {
    None, UIComponent, Data,
    ListView, WebView,
    Cache, Crash
}

data class MenuModel(val title: String, val detail: String, val url: String, val params: Map<String, Any>, val callback: (() -> Unit)?)