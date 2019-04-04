package com.cdts.demo.data.device.repository

import com.cdts.demo.schema.repository.BaseRepository
import com.cdts.oreo.data.device.ORDevice
import com.cdts.oreo.data.network.retrofit.ORNetClient
import com.cdts.oreo.data.network.retrofit.ORNetworkStatus
import io.reactivex.Observable

class DeviceInfoRepository: BaseRepository() {

    var networkType: ORNetworkStatus = ORNetworkStatus.Unknown
    var networkType2: ORNetworkStatus = ORNetworkStatus.Unknown
    var androidAdId = ""
    var googleEmailAccounts = ""
    var isFingerprintSensorPresent = ""
    var areFingerprintsEnrolled = ""
    var isNetworkAvailable = ""
    var ipv4OutsideAddress = ""
    var wifiSSID = ""
    var wifiLinkSpeed = ""
    var wifiBSSID = ""
    var wifiMAC = ""
    var bluetoothMAC = ""
    var imei = ""
    var phoneNo = ""
    var imsi = ""
    var simSerial = ""
    var activeMultiSimInfo = ""
    var isMultiSim = ""
    var numberOfActiveSim = ""
    var activeSimInfo = ""
    var lineNumber = ""

    fun fetchData(): ArrayList<DeviceInfoModel> {
        val data = arrayListOf<DeviceInfoModel>()
        data.add(DeviceInfoModel("Android Ad Id", androidAdId))
        data.add(DeviceInfoModel("Android Id", ORDevice.androidID))
        data.add(DeviceInfoModel("Pseudo Unique ID", ORDevice.pseudoUniqueID))
        data.add(DeviceInfoModel("User Agent", ORDevice.ua))
        data.add(DeviceInfoModel("GSFID", ORDevice.gsfid))
        data.add(DeviceInfoModel("Google Email Accounts", googleEmailAccounts))
        data.add(DeviceInfoModel("Build SDK Version", ORDevice.buildSDKVersion))
        data.add(DeviceInfoModel("Sensors", ORDevice.sensors))
        data.add(DeviceInfoModel("Is Fingerprint Sensor Present", isFingerprintSensorPresent))
        data.add(DeviceInfoModel("Are Fingerprints Enrolled", areFingerprintsEnrolled))
        data.add(DeviceInfoModel("Running On Emulator", ORDevice.isRunningOnEmulator))
        data.add(DeviceInfoModel("Time", ORDevice.time))
        data.add(DeviceInfoModel("Formatted Time", ORDevice.formattedTime))
        data.add(DeviceInfoModel("Up Time", ORDevice.upTime))
        data.add(DeviceInfoModel("Formatted Up Time", ORDevice.formattedUpTime))
        data.add(DeviceInfoModel("Current Date", ORDevice.currentDate))
        data.add(DeviceInfoModel("Formatted Date", ORDevice.formattedDate))
        data.add(DeviceInfoModel("Time Zone", ORDevice.timeZone))
        data.add(DeviceInfoModel("Has SDCard", ORDevice.hasSdCard))
        data.add(DeviceInfoModel("Ringer Mode", ORDevice.ringerMode))
        data.add(DeviceInfoModel("Developer Option Enabled", ORDevice.isDeveloperOptionEnabled))

        data.add(DeviceInfoModel("Network Available", isNetworkAvailable))
        data.add(DeviceInfoModel("WIFI Enabled", ORDevice.isWifiEnabled))
        data.add(DeviceInfoModel("IPv4 Address", ORDevice.ipv4Address))
        data.add(DeviceInfoModel("IPv6 Address", ORDevice.ipv6Address))
        data.add(DeviceInfoModel("IPv4 Outside Address", ipv4OutsideAddress))
        data.add(DeviceInfoModel("WIFI SSID", wifiSSID))
        data.add(DeviceInfoModel("WIFI Link Speed", wifiLinkSpeed))
        data.add(DeviceInfoModel("WIFI BSSID", wifiBSSID))
        data.add(DeviceInfoModel("WIFI MAC", wifiMAC))
        data.add(DeviceInfoModel("Connected WIFI MAC Address", ORDevice.connectedWifiMacAddress))
        data.add(DeviceInfoModel("Network Type", networkType.name))
        data.add(DeviceInfoModel("Network Type 2", networkType2.name))
        data.add(DeviceInfoModel("Total RAM", ORDevice.totalRAM))
        data.add(DeviceInfoModel("Available Internal Memory Size", ORDevice.availableInternalMemorySize))
        data.add(DeviceInfoModel("Available External Memory Size", ORDevice.availableExternalMemorySize))
        data.add(DeviceInfoModel("Total Internal Memory Size", ORDevice.totalInternalMemorySize))
        data.add(DeviceInfoModel("Total External Memory Size", ORDevice.totalExternalMemorySize))
        data.add(DeviceInfoModel("Activity Name", ORDevice.activityName))
        data.add(DeviceInfoModel("Package Name", ORDevice.packageName))
        data.add(DeviceInfoModel("Store", ORDevice.store))
        data.add(DeviceInfoModel("App Name", ORDevice.appName))
        data.add(DeviceInfoModel("App Version", ORDevice.appVersion))
        data.add(DeviceInfoModel("App Version Code", ORDevice.appVersionCode))
        data.add(DeviceInfoModel("Signature", ORDevice.signature))
        data.add(DeviceInfoModel("Is App Installed", ORDevice.isAppInstalled(ORDevice.packageName)))

        data.add(DeviceInfoModel("Battery Percentage", ORDevice.batteryPercentage))
        data.add(DeviceInfoModel("Is Device Charging", ORDevice.isDeviceCharging))
        data.add(DeviceInfoModel("Battery Technology", ORDevice.batteryTechnology))
        data.add(DeviceInfoModel("Battery Temperature", ORDevice.batteryTemperature))
        data.add(DeviceInfoModel("Battery Voltage", ORDevice.batteryVoltage))
        data.add(DeviceInfoModel("Is Battery Present", ORDevice.isBatteryPresent))
        data.add(DeviceInfoModel("Battery Health", ORDevice.batteryHealth))
        data.add(DeviceInfoModel("Charging Source", ORDevice.chargingSource))

        data.add(DeviceInfoModel("Bluetooth MAC", bluetoothMAC))
        data.add(DeviceInfoModel("String Supported ABIS", ORDevice.stringSupportedABIS))
        data.add(DeviceInfoModel("String Supported 32Bit ABIS", ORDevice.stringSupported32BitABIS))
        data.add(DeviceInfoModel("String Supported 64Bit ABIS", ORDevice.stringSupported64BitABIS))

        data.add(DeviceInfoModel("IMEI", imei))
        data.add(DeviceInfoModel("Screen Display ID", ORDevice.screenDisplayID))
        data.add(DeviceInfoModel("Build Version Codename", ORDevice.buildVersionCodename))
        data.add(DeviceInfoModel("Build Version Incremental", ORDevice.buildVersionIncremental))
        data.add(DeviceInfoModel("Build Version SDK", ORDevice.buildVersionSDK))
        data.add(DeviceInfoModel("Build ID", ORDevice.buildID))
        data.add(DeviceInfoModel("Manufacturer", ORDevice.manufacturer))
        data.add(DeviceInfoModel("Model", ORDevice.model))
        data.add(DeviceInfoModel("OS Codename", ORDevice.osCodename))
        data.add(DeviceInfoModel("OS Version", ORDevice.osVersion))
        data.add(DeviceInfoModel("Phone No", phoneNo))
        data.add(DeviceInfoModel("Radio Ver", ORDevice.radioVer))
        data.add(DeviceInfoModel("Product", ORDevice.product))
        data.add(DeviceInfoModel("Device", ORDevice.device))
        data.add(DeviceInfoModel("Board", ORDevice.board))
        data.add(DeviceInfoModel("Hardware", ORDevice.hardware))
        data.add(DeviceInfoModel("Bootloader", ORDevice.bootloader))
        data.add(DeviceInfoModel("Fingerprint", ORDevice.fingerprint))
        data.add(DeviceInfoModel("Is Device Rooted", ORDevice.isDeviceRooted))
        data.add(DeviceInfoModel("Build Brand", ORDevice.buildBrand))
        data.add(DeviceInfoModel("Build Host", ORDevice.buildHost))
        data.add(DeviceInfoModel("Build Tags", ORDevice.buildTags))
        data.add(DeviceInfoModel("Build Time", ORDevice.buildTime))
        data.add(DeviceInfoModel("Build User", ORDevice.buildUser))
        data.add(DeviceInfoModel("Build Version Release", ORDevice.buildVersionRelease))
        data.add(DeviceInfoModel("Phone Type", ORDevice.phoneType))
        data.add(DeviceInfoModel("Display Version", ORDevice.displayVersion))
        data.add(DeviceInfoModel("Language", ORDevice.language))
        data.add(DeviceInfoModel("Device Type", ORDevice.deviceType))
        data.add(DeviceInfoModel("Serial", ORDevice.serial))
        data.add(DeviceInfoModel("Serial Number", ORDevice.serialNumber))
        data.add(DeviceInfoModel("Orientation", ORDevice.orientation))
        data.add(DeviceInfoModel("Is Emulator", ORDevice.isEmulator))

        data.add(DeviceInfoModel("Display Resolution", ORDevice.displayResolution))
        data.add(DeviceInfoModel("Screen Density", ORDevice.screenDensity))
        data.add(DeviceInfoModel("Refresh Rate", ORDevice.refreshRate))
        data.add(DeviceInfoModel("Screen Physical Size", ORDevice.screenPhysicalSize))

        data.add(DeviceInfoModel("IMSI", imsi))
        data.add(DeviceInfoModel("Sim Serial", simSerial))
        data.add(DeviceInfoModel("Sim State", ORDevice.simState))
        data.add(DeviceInfoModel("Country", ORDevice.country))
        data.add(DeviceInfoModel("Carrier", ORDevice.carrier))
        data.add(DeviceInfoModel("Is Sim Network Locked", ORDevice.isSimNetworkLocked))
        data.add(DeviceInfoModel("Active Multi Sim Info", activeMultiSimInfo))
        data.add(DeviceInfoModel("Is Multi Sim", isMultiSim))
        data.add(DeviceInfoModel("Number Of Active Sim", numberOfActiveSim))
        data.add(DeviceInfoModel("Active Sim Info", activeSimInfo))
        data.add(DeviceInfoModel("Line Number", lineNumber))

        data.add(DeviceInfoModel("Is NFC Present", ORDevice.isNfcPresent))
        data.add(DeviceInfoModel("NFC Enabled", ORDevice.isNfcEnabled))

        return data
    }

    fun fetchNetwork(): Observable<ORNetworkStatus> {
        return Observable.create { sink ->
            ORNetClient.getNetworkStatus {
                networkType = it
                sink.onNext(it)
                sink.onComplete()
            }
        }
    }

}

data class DeviceInfoModel(val title: String, val detail: Any?)