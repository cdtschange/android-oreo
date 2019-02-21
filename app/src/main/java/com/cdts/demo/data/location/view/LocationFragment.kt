package com.cdts.demo.data.location.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cdts.demo.R
import com.cdts.demo.dagger.activity.DaggerFragmentComponent
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.data.device.repository.DeviceInfoModel
import com.cdts.demo.data.location.repository.LocationModel
import com.cdts.demo.data.location.viewmodel.LocationViewModel
import com.cdts.demo.schema.view.BaseListFragment
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import kotlinx.android.synthetic.main.fragment_list_with_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class LocationFragment: BaseListFragment() {
    @Inject
    lateinit var mViewModel: LocationViewModel
    override var viewModel: ORBaseViewModel = ORBaseViewModel()
        get() = mViewModel

    override var listViewType: ListViewType = ListViewType.None

    var title: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    override fun setupDagger() {
        super.setupDagger()
        val fragmentComponent = DaggerFragmentComponent.builder()
            .applicationComponent(MyApplication.applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()

        fragmentComponent.inject(this)
    }

    override fun setupNavigation() {
        super.setupNavigation()
        demoToolBar.centerText = title ?: ""
    }

    @SuppressLint("CheckResult")
    override fun loadData() {
        super.loadData()
        fetchData()
        GlobalScope.launch(Dispatchers.Main) {
            delay(200)
            mViewModel.fetchSystemLocation().subscribe({
                fetchData()
            }, {})
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v: View
        val viewHolder: ViewHolder
        val data = getItem(position) as LocationModel
        if (convertView == null) {
            v = LayoutInflater.from(parent?.context).inflate(R.layout.list_view_item, parent, false)
            viewHolder = ViewHolder(v)
            v.tag = viewHolder
        } else {
            v = convertView
            viewHolder = v.tag as ViewHolder
        }
        viewHolder.lblTitle.text = data.title
        viewHolder.lblDetail.text = data.detail?.toString() ?: ""
        viewHolder.imgArrow.visibility = View.GONE
        return v
    }

    class ViewHolder(itemView: View) {
        var lblTitle: TextView = itemView.findViewById(R.id.lblTitle) as TextView
        var lblDetail: TextView = itemView.findViewById(R.id.lblDetail) as TextView
        var imgArrow: ImageView = itemView.findViewById(R.id.imgArrow) as ImageView
    }
}