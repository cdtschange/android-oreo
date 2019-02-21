package com.cdts.demo.data.device.viewmodel

import android.os.Build
import com.cdts.demo.dagger.activity.DaggerViewModelComponent
import com.cdts.demo.dagger.activity.module.ViewModelModule
import com.cdts.demo.data.device.repository.DeviceInfoRepository
import com.cdts.demo.schema.viewmodel.BaseListViewModel
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.data.device.ORDevice
import com.cdts.oreo.data.network.retrofit.ORNetworkStatus
import com.cdts.oreo.ui.schema.repository.ORBaseRepository
import io.reactivex.Observable
import javax.inject.Inject

class DeviceInfoViewModel: BaseListViewModel() {
    @Inject
    lateinit var mRepository: DeviceInfoRepository
    override var repository: ORBaseRepository = mRepository

    override fun setupDagger() {
        super.setupDagger()
        val viewModelComponent = DaggerViewModelComponent.builder()
            .applicationComponent(MyApplication.applicationComponent)
            .viewModelModule(ViewModelModule(this))
            .build()

        viewModelComponent.inject(this)
    }
    var subscribe1: Any? = null
    var subscribe2: Any? = null
    var subscribe3: Any? = null
    var subscribe4: Any? = null
    var subscribe5: Any? = null
    var subscribe6: Any? = null
    var subscribe7: Any? = null
    var subscribe8: Any? = null
    var subscribe9: Any? = null
    var subscribe10: Any? = null
    var subscribe11: Any? = null
    var subscribe12: Any? = null
    var subscribe13: Any? = null
    var subscribe14: Any? = null
    var subscribe15: Any? = null
    var subscribe16: Any? = null
    var subscribe17: Any? = null
    var subscribe18: Any? = null
    var subscribe19: Any? = null
    var subscribe20: Any? = null
    var subscribe21: Any? = null
    var subscribe22: Any? = null
    override fun fetchData(): Observable<Any> {

        subscribe1 = ORDevice.androidAdId().subscribe({
            mRepository.androidAdId = it
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe2 = ORDevice.googleEmailAccounts().subscribe({
            mRepository.googleEmailAccounts = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            subscribe3 = ORDevice.isFingerprintSensorPresent().subscribe({
                mRepository.isFingerprintSensorPresent = it.toString()
                fetchStaticData().subscribe({}, {})
            }, {})
            subscribe4 = ORDevice.areFingerprintsEnrolled().subscribe({
                mRepository.areFingerprintsEnrolled = it.toString()
                fetchStaticData().subscribe({}, {})
            }, {})
        }
        subscribe5 = ORDevice.isNetworkAvailable().subscribe({
            mRepository.isNetworkAvailable = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe6 = ORDevice.ipv4OutsideAddress().subscribe({
            mRepository.ipv4OutsideAddress = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe7 = ORDevice.wifiSSID().subscribe({
            mRepository.wifiSSID = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe8 = ORDevice.wifiLinkSpeed().subscribe({
            mRepository.wifiLinkSpeed = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe9 = ORDevice.wifiBSSID().subscribe({
            mRepository.wifiBSSID = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe10 = ORDevice.wifiMAC().subscribe({
            mRepository.wifiMAC = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe11 = mRepository.fetchNetwork().subscribe({
            mRepository.networkType = it
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe12 = ORDevice.networkType().subscribe({
            mRepository.networkType2 = it
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe13 = ORDevice.bluetoothMAC().subscribe({
            mRepository.bluetoothMAC = it
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe14 = ORDevice.imei().subscribe({
            mRepository.imei = it
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe15 = ORDevice.phoneNo().subscribe({
            mRepository.phoneNo = it
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe16 = ORDevice.imsi().subscribe({
            mRepository.imsi = it
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe17 = ORDevice.simSerial().subscribe({
            mRepository.simSerial = it
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe18 = ORDevice.activeMultiSimInfo().subscribe({
            mRepository.activeMultiSimInfo = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe19 = ORDevice.isMultiSim().subscribe({
            mRepository.isMultiSim = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe20 = ORDevice.numberOfActiveSim().subscribe({
            mRepository.numberOfActiveSim = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe21 = ORDevice.activeSimInfo().subscribe({
            mRepository.activeSimInfo = it.toString()
            fetchStaticData().subscribe({}, {})
        }, {})
        subscribe22 = ORDevice.lineNumber().subscribe({
            mRepository.lineNumber = it
            fetchStaticData().subscribe({}, {})
        }, {})
        return fetchStaticData()
    }
    fun fetchStaticData(): Observable<Any> {
        return Observable.just(mRepository.fetchData()).map { data ->
            appendDataArray(data)
            data
        }
    }

    fun fetchNetwork(): Observable<ORNetworkStatus> {
        return mRepository.fetchNetwork()
    }
}