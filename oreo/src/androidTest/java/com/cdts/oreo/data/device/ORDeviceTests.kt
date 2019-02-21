package com.cdts.oreo.data.device

import android.Manifest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.view.MotionEvent
import com.cdts.oreo.BaseTestCase
import com.cdts.oreo.test.MainTestActivity
import org.junit.Rule
import org.junit.Test

class ORDeviceTests: BaseTestCase() {
    @get:Rule
    var activityRule: ActivityTestRule<MainTestActivity> = ActivityTestRule(MainTestActivity::class.java,true,true)
    @get:Rule
    val permissionRuleGETACCOUNTS = GrantPermissionRule.grant(Manifest.permission.GET_ACCOUNTS)
    @get:Rule
    val permissionRuleUSEFINGERPRINT = GrantPermissionRule.grant(Manifest.permission.USE_FINGERPRINT)
    @get:Rule
    val permissionRuleACCESSNETWORKSTATE = GrantPermissionRule.grant(Manifest.permission.ACCESS_NETWORK_STATE)
    @get:Rule
    val permissionRuleACCESSWIFISTATE = GrantPermissionRule.grant(Manifest.permission.ACCESS_WIFI_STATE)
    @get:Rule
    val permissionRuleINTERNET = GrantPermissionRule.grant(Manifest.permission.INTERNET)
    @get:Rule
    val permissionRuleBLUETOOTH = GrantPermissionRule.grant(Manifest.permission.BLUETOOTH)
    @get:Rule
    val permissionRuleREADPHONESTATE = GrantPermissionRule.grant(Manifest.permission.READ_PHONE_STATE)

    @Test
    fun testDevice() {
        ORDevice.androidAdId().subscribe({ }, { })
        print(ORDevice.androidID)
        print(ORDevice.pseudoUniqueID)
        print(ORDevice.ua)
        print(ORDevice.gsfid)
        ORDevice.googleEmailAccounts().subscribe({ }, { })
        print(ORDevice.buildSDKVersion)
        print(ORDevice.sensors)
        ORDevice.isFingerprintSensorPresent().subscribe({ }, { })
        ORDevice.areFingerprintsEnrolled().subscribe({ }, { })
        print(ORDevice.isRunningOnEmulator)
        print(ORDevice.time)
        print(ORDevice.formattedTime)
        print(ORDevice.upTime)
        print(ORDevice.formattedUpTime)
        print(ORDevice.currentDate)
        print(ORDevice.formattedDate)
        print(ORDevice.timeZone)
        print(ORDevice.hasSdCard)
        print(ORDevice.ringerMode)
        print(ORDevice.isDeveloperOptionEnabled)
        ORDevice.isNetworkAvailable().subscribe({ }, { })
        print(ORDevice.isWifiEnabled)
        print(ORDevice.ipv4Address)
        print(ORDevice.ipv6Address)
        ORDevice.ipv4OutsideAddress().subscribe({ }, { })
        ORDevice.wifiSSID().subscribe({ }, { })
        ORDevice.wifiLinkSpeed().subscribe({ }, { })
        ORDevice.wifiBSSID().subscribe({ }, { })
        ORDevice.wifiMAC().subscribe({ }, { })
        print(ORDevice.connectedWifiMacAddress)
        ORDevice.networkType().subscribe({ }, { })
        print(ORDevice.totalRAM)
        print(ORDevice.availableInternalMemorySize)
        print(ORDevice.availableExternalMemorySize)
        print(ORDevice.totalInternalMemorySize)
        print(ORDevice.totalExternalMemorySize)
        print(ORDevice.activityName)
        print(ORDevice.packageName)
        print(ORDevice.store)
        print(ORDevice.appName)
        print(ORDevice.appVersion)
        print(ORDevice.appVersionCode)
        print(ORDevice.signature)
        print(ORDevice.isAppInstalled(ORDevice.packageName))

        print(ORDevice.batteryPercentage)
        print(ORDevice.isDeviceCharging)
        print(ORDevice.batteryTechnology)
        print(ORDevice.batteryTemperature)
        print(ORDevice.batteryVoltage)
        print(ORDevice.isBatteryPresent)
        print(ORDevice.batteryHealth)
        print(ORDevice.chargingSource)

        ORDevice.bluetoothMAC().subscribe({ }, { })

        print(ORDevice.stringSupportedABIS)
        print(ORDevice.stringSupported32BitABIS)
        print(ORDevice.stringSupported64BitABIS)

        ORDevice.imei().subscribe({ }, { })
        print(ORDevice.screenDisplayID)
        print(ORDevice.buildVersionCodename)
        print(ORDevice.buildVersionIncremental)
        print(ORDevice.buildVersionSDK)
        print(ORDevice.buildID)
        print(ORDevice.manufacturer)
        print(ORDevice.model)
        print(ORDevice.osCodename)
        print(ORDevice.osVersion)
        ORDevice.phoneNo().subscribe({ }, { })
        print(ORDevice.radioVer)
        print(ORDevice.product)
        print(ORDevice.device)
        print(ORDevice.board)
        print(ORDevice.hardware)
        print(ORDevice.bootloader)
        print(ORDevice.fingerprint)
        print(ORDevice.isDeviceRooted)
        print(ORDevice.buildBrand)
        print(ORDevice.buildHost)
        print(ORDevice.buildTags)
        print(ORDevice.buildTime)
        print(ORDevice.buildUser)
        print(ORDevice.buildVersionRelease)
        print(ORDevice.phoneType)
        print(ORDevice.displayVersion)
        print(ORDevice.language)
        print(ORDevice.deviceType)
        print(ORDevice.serial)
        print(ORDevice.serialNumber)
        print(ORDevice.orientation)

        print(ORDevice.displayResolution)
        print(ORDevice.screenDensity)
        print(ORDevice.getDisplayXYCoordinate(MotionEvent.obtain(0,0,0,0f,0f,0f,0f,0,0f,0f,0,0)))
        print(ORDevice.refreshRate)
        print(ORDevice.screenPhysicalSize)

        ORDevice.imsi().subscribe({ }, { })
        ORDevice.simSerial().subscribe({ }, { })
        print(ORDevice.simState)
        print(ORDevice.country)
        print(ORDevice.carrier)
        print(ORDevice.isSimNetworkLocked)
        ORDevice.activeMultiSimInfo().subscribe({ }, { })
        ORDevice.isMultiSim().subscribe({ }, { })
        ORDevice.numberOfActiveSim().subscribe({ }, { })
        ORDevice.activeSimInfo().subscribe({ }, { })
        ORDevice.lineNumber().subscribe({ }, { })

        print(ORDevice.isNfcPresent)
        print(ORDevice.isNfcEnabled)
    }
}