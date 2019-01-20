package com.cdts.demo.ui.tab.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.cdts.demo.R
import com.cdts.demo.dagger.activity.DaggerFragmentComponent
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.schema.view.BaseListFragment
import com.cdts.demo.ui.application.MyApplication
import com.cdts.demo.ui.tab.repository.MenuModel
import com.cdts.demo.ui.tab.repository.MenuType
import com.cdts.demo.ui.tab.viewmodel.MenuListViewModel
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import kotlinx.android.synthetic.main.fragment_list_with_toolbar.*
import javax.inject.Inject

class UIFragment : BaseListFragment() {

    @Inject
    lateinit var mViewModel: MenuListViewModel
    override var viewModel: ORBaseViewModel = mViewModel

    override var listViewType: ListViewType = ListViewType.None

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

    override fun setupUI() {
        super.setupUI()
        mViewModel.type = MenuType.UI
    }

    override fun setupNavigation() {
        super.setupNavigation()
        demoToolBar.centerText = resources.getString(R.string.ui)
        demoToolBar.showBackButton = false
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchData()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v: View
        val viewHolder: ViewHolder
        val data = getItem(position) as MenuModel
        if (convertView == null) {
            v = LayoutInflater.from(parent?.context).inflate(R.layout.list_view_item, parent, false)
            viewHolder = ViewHolder(v)
            v.tag = viewHolder
        } else {
            v = convertView
            viewHolder = v.tag as ViewHolder
        }
        viewHolder.lblTitle.text = data.title
        viewHolder.lblDetail.text = data.detail
        return v
    }

    override fun itemClickEvent(listView: ListView, view: View, position: Int, id: Long) {
        val data = getItem(position) as MenuModel
        if (data.url.startsWith("http")) {
//            ORRouter.routeToUrl(data.url)
        } else {
//            ORRouter.routeToName(data.url)
        }
    }

    class ViewHolder(itemView: View) {
        var lblTitle: TextView = itemView.findViewById(R.id.lblTitle) as TextView
        var lblDetail: TextView = itemView.findViewById(R.id.lblDetail) as TextView
    }
}