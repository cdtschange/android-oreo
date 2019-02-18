package com.cdts.demo.ui.listview.view

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cdts.demo.R
import com.cdts.demo.dagger.activity.DaggerFragmentComponent
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.schema.view.BaseListFragment
import com.cdts.demo.tab.repository.MenuModel
import com.cdts.demo.ui.application.MyApplication
import com.cdts.demo.ui.listview.viewmodel.ListTypeViewModel
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import kotlinx.android.synthetic.main.fragment_list_with_toolbar.*
import javax.inject.Inject


class ListTypeFragment: BaseListFragment() {
    @Inject
    lateinit var mViewModel: ListTypeViewModel
    override var viewModel: ORBaseViewModel = ORBaseViewModel()
        get() = mViewModel

    override var listViewType: ListViewType = ListViewType.None

    var type: String? = null
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

    override fun setupBinding(binding: ViewDataBinding?) {
        super.setupBinding(binding)
        listViewType = ListViewType.valueOf(type ?: ListViewType.None.name)
    }

    override fun loadData() {
        super.loadData()
        when(listViewType) {
            ListViewType.None, ListViewType.LoadMoreOnly -> fetchData()
            else -> autoRefresh()
        }
    }

    override fun willFetchListData() {
        super.willFetchListData()
        if (listViewType == ListViewType.None || mViewModel.dataIndex == 0 && listViewType == ListViewType.LoadMoreOnly) {
            showIndicator()
        }
    }

    override fun didFetchListData(data: Any) {
        super.didFetchListData(data)
        if (listViewType == ListViewType.None || mViewModel.dataIndex == 0 && listViewType == ListViewType.LoadMoreOnly) {
            hideIndicator()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v: View
        val viewHolder: ViewHolder
        val data = getItem(position) as String
        if (convertView == null) {
            v = LayoutInflater.from(parent?.context).inflate(R.layout.list_view_item, parent, false)
            viewHolder = ViewHolder(v)
            v.tag = viewHolder
        } else {
            v = convertView
            viewHolder = v.tag as ViewHolder
        }
        viewHolder.lblTitle.text = data
        viewHolder.lblDetail.visibility = View.GONE
        viewHolder.imgArrow.visibility = View.GONE
        return v
    }

    class ViewHolder(itemView: View) {
        var lblTitle: TextView = itemView.findViewById(R.id.lblTitle) as TextView
        var lblDetail: TextView = itemView.findViewById(R.id.lblDetail) as TextView
        var imgArrow: ImageView = itemView.findViewById(R.id.imgArrow) as ImageView
    }
}