package com.cdts.demo.ui.tab.repository

import com.cdts.demo.schema.repository.BaseRepository
import javax.inject.Inject

class MenuListRepository @Inject constructor(): BaseRepository() {


    fun fetchMenuItems(type: MenuType): List<MenuModel> {
        when(type) {
            MenuType.UI -> {
                val data = mutableListOf<MenuModel>()
//        data.add(ListItem("Tab", "Navigation&Menu", TabActivity::class.java.name))
//        data.add(ListItem("Toolbar", "Toolbar", ToolBarActivity::class.java.name))
//        data.add(ListItem("Hud", "KProgressHUD", HudActivity::class.java.name))
//        data.add(ListItem("Alert", "Flyco Dialog", AlertActivity::class.java.name))
//        data.add(ListItem("Lifecycle", "Activity", LifecycleActivity::class.java.name))
//        data.add(ListItem("List", "Pull To Refresh", PullToRefreshListActivity::class.java.name))
//        data.add(ListItem("WebView", "WebView", WebViewListActivity::class.java.name))
//        data.add(ListItem("Captcha", "Captcha Image", CaptchaActivity::class.java.name))
//        data.add(ListItem("Counting Button", "Button", CountingButtonActivity::class.java.name))
//        data.add(ListItem("Banner", "Youth Banner", BannerActivity::class.java.name))
//        data.add(ListItem("Wheel Picker", "Aigestudio", WheelPickerActivity::class.java.name))
//        data.add(ListItem("Float Window", "Float Window", FloatWindowActivity::class.java.name))
                return data
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
                return data
            }
            else -> return listOf()
        }
    }
}

enum class MenuType {
    None, UI, Data
}

data class MenuModel(val title: String, val detail: String, val url: String, val params: Map<String, Any>, val callback: (() -> Unit)?)