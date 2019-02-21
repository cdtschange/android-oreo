package com.cdts.oreo.data.device

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.location.Location
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.telephony.SubscriptionInfo
import android.telephony.TelephonyManager
import android.view.MotionEvent
import com.cdts.oreo.data.network.ORError
import com.cdts.oreo.data.network.ORStatusCode
import com.cdts.oreo.data.network.retrofit.ORNetworkStatus
import com.cdts.oreo.data.permission.ORPermission
import com.cdts.oreo.ui.application.ORApplication
import com.cdts.oreo.ui.router.ORRouter
import github.nisrulz.easydeviceinfo.ads.EasyAdsMod
import github.nisrulz.easydeviceinfo.base.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object ORDevice {
    // AdsMod
    fun androidAdId(): Observable<String> {
        return Observable.create { sink ->
            EasyAdsMod(ORApplication.application).getAndroidAdId { adIdentifier, adDonotTrack ->
                if (!adDonotTrack) {
                    Timber.i("AndroidAdId: $adIdentifier")
                    sink.onNext(adIdentifier)
                    sink.onComplete()
                } else {
                    Timber.e("AndroidAdId: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "AD does not allow to be tracked"))
                }
            }
        }
    }

    // IdMod
    val pseudoUniqueID: String
        get() = EasyIdMod(ORApplication.application).pseudoUniqueID
    val androidID: String
        get() = EasyIdMod(ORApplication.application).androidID
    val ua: String
        get() = EasyIdMod(ORApplication.application).ua
    val gsfid: String
        get() = EasyIdMod(ORApplication.application).gsfid
    fun googleEmailAccounts(): Observable<Array<String>> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.GET_ACCOUNTS) { success ->
                if (success) {
                    val value = EasyIdMod(ORApplication.application).accounts
                    Timber.i("Google Email Accounts: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Google Email Accounts: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Google Email Accounts permission denied"))
                }
            }
        }
    }

    val buildSDKVersion : Int
        get() = Build.VERSION.SDK_INT

    // SensorMod
    val sensors: List<Sensor>
        get() = EasySensorMod(ORApplication.application).allSensors

    // FingerprintMod
    @RequiresApi(Build.VERSION_CODES.M)
    fun isFingerprintSensorPresent(): Observable<Boolean> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.USE_FINGERPRINT) { success ->
                if (success) {
                    val value = EasyFingerprintMod(ORApplication.application).isFingerprintSensorPresent
                    Timber.i("Is Fingerprint Sensor present: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Is Fingerprint Sensor present: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Is Fingerprint Sensor present permission denied"))
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun areFingerprintsEnrolled(): Observable<Boolean> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.USE_FINGERPRINT) { success ->
                if (success) {
                    val value = EasyFingerprintMod(ORApplication.application).areFingerprintsEnrolled()
                    Timber.i("Are fingerprints enrolled: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Are fingerprints enrolled: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Are fingerprints enrolled permission denied"))
                }
            }
        }
    }

    // ConfigMod
    val isRunningOnEmulator: Boolean
        get() = EasyConfigMod(ORApplication.application).isRunningOnEmulator
    val time: Long
        get() = EasyConfigMod(ORApplication.application).time
    val formattedTime: String
        get() = EasyConfigMod(ORApplication.application).formattedTime
    /**
     * 这个值记录了系统启动到当前时刻经过的时间。
     * 但是系统深度睡眠(CPU睡眠，黑屏，系统等待唤醒)之中的时间不算在内。
     * 这个值不受系统时间设置，电源策略等因素的影响，因此它是大多数时间间隔统计的基础。
     * 系统保证了这个值只增长不下降，所以它适合所有的不包括系统睡眠时间的时间间隔统计。
     */
    val upTime: Long
        get() = EasyConfigMod(ORApplication.application).upTime
    val formattedUpTime: String
        get() = EasyConfigMod(ORApplication.application).formattedUpTime
    val currentDate: Date
        get() = EasyConfigMod(ORApplication.application).currentDate
    val formattedDate: String
        get() = EasyConfigMod(ORApplication.application).formattedDate
    val timeZone: String
        get() = "${TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT)}:${TimeZone.getDefault().id}"
    val hasSdCard: Boolean
        get() = EasyConfigMod(ORApplication.application).hasSdCard()

    val ringerMode: String
        get() = {
            val mode = EasyConfigMod(ORApplication.application).deviceRingerMode
            when(mode) {
                RingerMode.NORMAL -> "Normal"
                RingerMode.VIBRATE -> "Vibrate"
                RingerMode.SILENT -> "Silent"
                else -> "Unknown"
            }
        }()
    val isDeveloperOptionEnabled: Boolean
        get() {
            return Settings.Secure.getInt(ORApplication.application.contentResolver,
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0) != 0
        }

    // NetworkMod
    fun isNetworkAvailable(): Observable<Boolean> {
        return Observable.create { sink ->
            ORPermission.guard(arrayOf(Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET)) { success ->
                if (success) {
                    val value = EasyNetworkMod(ORApplication.application).isNetworkAvailable
                    Timber.i("Is Network Available: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Is Network Available: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Is Network Available permission denied"))
                }
            }
        }
    }
    val isWifiEnabled: Boolean
        get() = EasyNetworkMod(ORApplication.application).isWifiEnabled
    val ipv4Address: String
        get() = EasyNetworkMod(ORApplication.application).iPv4Address
    val ipv6Address: String
        get() = EasyNetworkMod(ORApplication.application).iPv6Address
    /**
     * 外网IP
     */
    fun ipv4OutsideAddress(): Observable<String> {
        return Observable.create<String> { sink ->
            var ip = ""
            try {
                val infoUrl = URL("http://pv.sohu.com/cityjson?ie=utf-8")
                val connection = infoUrl.openConnection()
                val httpConnection = connection as HttpURLConnection
                val responseCode = httpConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inStream: InputStream = httpConnection.inputStream
                    val reader: BufferedReader? = BufferedReader(InputStreamReader(inStream, "utf-8"))
                    val strber = StringBuilder()
                    var line: String = reader?.readLine() ?: ""
                    while (!line.isEmpty()) {
                        strber.append(line + "\n")
                        line = reader?.readLine() ?: ""
                    }
                    inStream.close()
                    // 从反馈的结果中提取出IP地址
                    val start = strber.indexOf("{")
                    val end = strber.indexOf("}")
                    val json = strber.substring(start, end + 1)
                    if (json != null) {
                        try {
                            val jsonObject = JSONObject(json)
                            ip = jsonObject.optString("cip")
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
                sink.onNext(ip)
                sink.onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
                sink.onError(e)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
    fun wifiSSID(): Observable<String> {
        return Observable.create { sink ->
            ORPermission.guard(arrayOf(Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE)) { success ->
                if (success) {
                    val value = EasyNetworkMod(ORApplication.application).wifiSSID
                    Timber.i("WiFi SSID: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("WiFi SSID: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "WiFi SSID permission denied"))
                }
            }
        }
    }
    fun wifiLinkSpeed(): Observable<String> {
        return Observable.create { sink ->
            ORPermission.guard(arrayOf(Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE)) { success ->
                if (success) {
                    val value = EasyNetworkMod(ORApplication.application).wifiLinkSpeed
                    Timber.i("WiFi Link Speed: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("WiFi Link Speed: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "WiFi Link Speed permission denied"))
                }
            }
        }
    }
    fun wifiBSSID(): Observable<String> {
        return Observable.create { sink ->
            ORPermission.guard(arrayOf(Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE)) { success ->
                if (success) {
                    val value = EasyNetworkMod(ORApplication.application).wifiBSSID
                    Timber.i("WiFi BSSID: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("WiFi BSSID: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "WiFi BSSID permission denied"))
                }
            }
        }
    }
    fun wifiMAC(): Observable<String> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.ACCESS_WIFI_STATE) { success ->
                if (success) {
                    val value = EasyNetworkMod(ORApplication.application).wifiMAC
                    Timber.i("WiFi MAC Address: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("WiFi MAC Address: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "WiFi MAC Address permission denied"))
                }
            }
        }
    }
    val connectedWifiMacAddress: String
        get() {
            var connectedWifiMacAddress = ""
            val wifiManager = ORApplication.application.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager

            if (wifiManager != null) {
                val wifiList = wifiManager.scanResults
                val info = wifiManager.connectionInfo
                if (wifiList != null && info != null) {
                    for (result in wifiList) {
                        if (info.bssid == result.BSSID) {
                            connectedWifiMacAddress = result.BSSID
                        }
                    }
                }
            }
            return connectedWifiMacAddress
        }

    fun networkType(): Observable<ORNetworkStatus> {
        return Observable.create { sink ->
            ORPermission.guard(arrayOf(Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET)) { success ->
                if (success) {
                    val value = EasyNetworkMod(ORApplication.application).networkType
                    Timber.i("Network Type: $value")
                    val type: ORNetworkStatus = when(value) {
                        NetworkType.CELLULAR_UNKNOWN -> ORNetworkStatus.Unknown
                        NetworkType.CELLULAR_UNIDENTIFIED_GEN -> ORNetworkStatus.Unknown
                        NetworkType.CELLULAR_2G -> ORNetworkStatus.Data2G
                        NetworkType.CELLULAR_3G -> ORNetworkStatus.Data3G
                        NetworkType.CELLULAR_4G -> ORNetworkStatus.Data4G
                        NetworkType.WIFI_WIFIMAX -> ORNetworkStatus.Wifi
                        NetworkType.UNKNOWN -> ORNetworkStatus.Unknown
                        else -> ORNetworkStatus.Unknown
                    }
                    sink.onNext(type)
                    sink.onComplete()
                } else {
                    Timber.e("Network Type: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Network Type permission denied"))
                }
            }
        }
    }

    // MemoryMod
    val totalRAM: Long
        get() = EasyMemoryMod(ORApplication.application).totalRAM
    val availableInternalMemorySize: Long
        get() = EasyMemoryMod(ORApplication.application).availableInternalMemorySize
    val availableExternalMemorySize: Long
        get() = EasyMemoryMod(ORApplication.application).availableExternalMemorySize
    val totalInternalMemorySize: Long
        get() = EasyMemoryMod(ORApplication.application).totalInternalMemorySize
    val totalExternalMemorySize: Long
        get() = EasyMemoryMod(ORApplication.application).totalExternalMemorySize

    // AppMod
    val activityName: String
        get() = EasyAppMod(ORRouter.topActivity()).activityName
    val packageName: String
        get() = EasyAppMod(ORApplication.application).packageName
    val store: String
        get() = EasyAppMod(ORApplication.application).store
    val appName: String
        get() = EasyAppMod(ORApplication.application).appName
    val appVersion: String
        get() = EasyAppMod(ORApplication.application).appVersion
    val appVersionCode: String
        get() = EasyAppMod(ORApplication.application).appVersionCode

    /**
     * 获取签名
     */
    val signature: String
        @SuppressLint("PackageManagerGetSiingnatures")
        get() {
            var value = ""
            try {
                /** 通过包管理器获得指定包名包含签名的包信息  */
                val packageInfo = ORApplication.application.packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
                /******* 通过返回的包信息获得签名数组  */
                val signatures = packageInfo.signatures
                val builder = StringBuilder()
                /******* 循环遍历签名数组拼接应用签名  */
                for (i in signatures) {
                    builder.append(i.toCharsString())
                }
                /************** 得到应用签名  */
                value = builder.toString()
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return value
        }
    fun isAppInstalled(appPackageName: String): Boolean {
        return EasyAppMod(ORApplication.application).isAppInstalled(appPackageName)
    }

    // BatteryMod
    val batteryPercentage: Int
        get() = EasyBatteryMod(ORApplication.application).batteryPercentage
    val isDeviceCharging: Boolean
        get() = EasyBatteryMod(ORApplication.application).isDeviceCharging
    val batteryTechnology: String
        get() = EasyBatteryMod(ORApplication.application).batteryTechnology
    val batteryTemperature: Float
        get() = EasyBatteryMod(ORApplication.application).batteryTemperature
    val batteryVoltage: Int
        get() = EasyBatteryMod(ORApplication.application).batteryVoltage
    val isBatteryPresent: Boolean
        get() = EasyBatteryMod(ORApplication.application).isBatteryPresent
    val batteryHealth: String
        get() = {
            val mode = EasyBatteryMod(ORApplication.application).batteryHealth
            when(mode) {
                BatteryHealth.GOOD -> "Good"
                BatteryHealth.HAVING_ISSUES -> "Having issues"
                else -> "Having issues"
            }
        }()
    val chargingSource: String
        get() = {
            val mode = EasyBatteryMod(ORApplication.application).chargingSource
            when(mode) {
                ChargingVia.AC -> "AC"
                ChargingVia.USB -> "USB"
                ChargingVia.WIRELESS -> "Wireless"
                ChargingVia.UNKNOWN_SOURCE -> "Unknown Source"
                else -> "Unknown Source"
            }
        }()

    // BluetoothMod
    fun bluetoothMAC(): Observable<String> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.BLUETOOTH) { success ->
                if (success) {
                    val value = EasyBluetoothMod(ORApplication.application).bluetoothMAC
                    Timber.i("Bluetooth MAC: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Bluetooth MAC: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Bluetooth MAC permission denied"))
                }
            }
        }
    }

    // Cpu
    val stringSupportedABIS: String
        get() = EasyCpuMod().stringSupportedABIS
    val stringSupported32BitABIS: String
        get() = EasyCpuMod().stringSupported32bitABIS
    val stringSupported64BitABIS: String
        get() = EasyCpuMod().stringSupported64bitABIS

    // DeviceMod
    @SuppressLint("MissingPermission")
    fun imei(): Observable<String> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.READ_PHONE_STATE) { success ->
                if (success) {
                    val value = EasyDeviceMod(ORApplication.application).imei
                    Timber.i("IMEI: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("IMEI: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "IMEI permission denied"))
                }
            }
        }
    }
    val screenDisplayID: String
        get() = EasyDeviceMod(ORApplication.application).screenDisplayID
    val buildVersionCodename: String
        get() = EasyDeviceMod(ORApplication.application).buildVersionCodename
    val buildVersionIncremental: String
        get() = EasyDeviceMod(ORApplication.application).buildVersionIncremental
    val buildVersionSDK: Int
        get() = EasyDeviceMod(ORApplication.application).buildVersionSDK
    val buildID: String
        get() = EasyDeviceMod(ORApplication.application).buildID
    val manufacturer: String
        get() = EasyDeviceMod(ORApplication.application).manufacturer
    val model: String
        get() = EasyDeviceMod(ORApplication.application).model
    val osCodename: String
        get() = EasyDeviceMod(ORApplication.application).osCodename
    val osVersion: String
        get() = EasyDeviceMod(ORApplication.application).osVersion
    @SuppressLint("MissingPermission")
    fun phoneNo(): Observable<String> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.READ_PHONE_STATE) { success ->
                if (success) {
                    val value = EasyDeviceMod(ORApplication.application).phoneNo
                    Timber.i("Phone Number: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Phone Number: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Phone Number permission denied"))
                }
            }
        }
    }
    /**
     * 基带版本
     */
    val radioVer: String
        get() = EasyDeviceMod(ORApplication.application).radioVer
    val product: String
        get() = EasyDeviceMod(ORApplication.application).product
    val device: String
        get() = EasyDeviceMod(ORApplication.application).device
    val board: String
        get() = EasyDeviceMod(ORApplication.application).board
    val hardware: String
        get() = EasyDeviceMod(ORApplication.application).hardware
    val bootloader: String
        get() = EasyDeviceMod(ORApplication.application).bootloader
    val fingerprint: String
        get() = EasyDeviceMod(ORApplication.application).fingerprint
    val isDeviceRooted: Boolean
        get() = EasyDeviceMod(ORApplication.application).isDeviceRooted
    val buildBrand: String
        get() = EasyDeviceMod(ORApplication.application).buildBrand
    val buildHost: String
        get() = EasyDeviceMod(ORApplication.application).buildHost
    val buildTags: String
        get() = EasyDeviceMod(ORApplication.application).buildTags
    val buildTime: Long
        get() = EasyDeviceMod(ORApplication.application).buildTime
    val buildUser: String
        get() = EasyDeviceMod(ORApplication.application).buildUser
    val buildVersionRelease: String
        get() = EasyDeviceMod(ORApplication.application).buildVersionRelease
    val phoneType: String
        get() = {
            val mode = EasyDeviceMod(ORApplication.application).phoneType
            when(mode) {
                PhoneType.GSM -> "GSM"
                PhoneType.CDMA -> "CDMA"
                PhoneType.NONE -> "None"
                else -> "Unknown"
            }
        }()
    val displayVersion: String
        get() = EasyDeviceMod(ORApplication.application).displayVersion
    val language: String
        get() = EasyDeviceMod(ORApplication.application).language
    val deviceType: String
        get() = {
            val mode = EasyDeviceMod(ORApplication.application).getDeviceType(ORRouter.topActivity())
            when(mode) {
                DeviceType.TABLET -> "Tablet"
                DeviceType.TV -> "TV"
                DeviceType.PHABLET -> "Phablet"
                DeviceType.PHONE -> "Phone"
                DeviceType.WATCH -> "Watch"
                else -> "Unknown"
            }
        }()
    val serial: String
        get() = EasyDeviceMod(ORApplication.application).serial
    val serialNumber: String
        get() {
            val value = try {
                val c = Class.forName("android.os.SystemProperties")
                val get = c.getMethod("get", String::class.java, String::class.java)
                get.invoke(c, "sys.serialnumber", "Unknown") as String
            } catch (ignored: Exception) {
                "Unknown"
            }
            return value
        }
    val orientation: String
        get() = {
            val mode = EasyDeviceMod(ORApplication.application).getOrientation(ORRouter.topActivity())
            when(mode) {
                OrientationType.LANDSCAPE -> "Landscape"
                OrientationType.PORTRAIT -> "Portrait"
                OrientationType.UNKNOWN -> "Unknown"
                else -> "Unknown"
            }
        }()

    // DisplayMod
    val displayResolution: String
        get() = EasyDisplayMod(ORApplication.application).resolution
    val screenDensity: String
        get() = EasyDisplayMod(ORApplication.application).density
    fun getDisplayXYCoordinate(event: MotionEvent): IntArray {
        return EasyDisplayMod(ORApplication.application).getDisplayXYCoordinates(event)
    }
    val refreshRate: Float
        get() = EasyDisplayMod(ORApplication.application).refreshRate
    val screenPhysicalSize: Float
        get() = EasyDisplayMod(ORApplication.application).physicalSize

    // SimMod
    @SuppressLint("MissingPermission")
    fun imsi(): Observable<String> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.READ_PHONE_STATE) { success ->
                if (success) {
                    val value = EasySimMod(ORApplication.application).imsi
                    Timber.i("IMSI: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("IMSI: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "IMSI permission denied"))
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun simSerial(): Observable<String> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.READ_PHONE_STATE) { success ->
                if (success) {
                    val value = EasySimMod(ORApplication.application).simSerial
                    Timber.i("SIM Serial Number: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("SIM Serial Number: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "SIM Serial Number permission denied"))
                }
            }
        }
    }
    val simState: Int
        get() {
            return try {
                (ORApplication.application.applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).simState
            } catch (e: Exception) {
                0
            }
        }
    val country: String
        get() = EasySimMod(ORApplication.application).country
    val carrier: String
        get() = EasySimMod(ORApplication.application).carrier
    val isSimNetworkLocked: Boolean
        get() = EasySimMod(ORApplication.application).isSimNetworkLocked
    @SuppressLint("MissingPermission")
    fun activeMultiSimInfo(): Observable<List<SubscriptionInfo>> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.READ_PHONE_STATE) { success ->
                if (success) {
                    val value = EasySimMod(ORApplication.application).activeMultiSimInfo
                    Timber.i("Get Active SimInfo: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Get Active SimInfo: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Get Active SimInfo permission denied"))
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun isMultiSim(): Observable<Boolean> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.READ_PHONE_STATE) { success ->
                if (success) {
                    val value = EasySimMod(ORApplication.application).isMultiSim
                    Timber.i("Is MultiSim: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Is MultiSim: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Is MultiSim permission denied"))
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun numberOfActiveSim(): Observable<Int> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.READ_PHONE_STATE) { success ->
                if (success) {
                    val value = EasySimMod(ORApplication.application).numberOfActiveSim
                    Timber.i("Get number of active SIM: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Get number of active SIM: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Get number of active SIM permission denied"))
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun activeSimInfo(): Observable<List<SubscriptionInfo>> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.READ_PHONE_STATE) { success ->
                if (success) {
                    val value = EasySimMod(ORApplication.application).activeMultiSimInfo
                    Timber.i("Get active SIM: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Get active SIM: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Get active SIM permission denied"))
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun lineNumber(): Observable<String> {
        return Observable.create { sink ->
            ORPermission.guard(Manifest.permission.READ_PHONE_STATE) { success ->
                if (success) {
                    val tMgr = ORApplication.application.applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    val value = tMgr.line1Number
                    Timber.i("Get line number: $value")
                    sink.onNext(value)
                    sink.onComplete()
                } else {
                    Timber.e("Get line number: not allowed")
                    sink.onError(ORError(ORStatusCode.ValidateFailed.value, "Get line number permission denied"))
                }
            }
        }
    }

    fun isMockLocation(location: Location): Boolean {
        return location.isFromMockProvider
    }

    // NfcMod
    val isNfcPresent: Boolean
        get() = EasyNfcMod(ORApplication.application).isNfcPresent
    val isNfcEnabled: Boolean
        get() = EasyNfcMod(ORApplication.application).isNfcEnabled

}