package com.cdts.demo.data.cache.view

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.cdts.demo.R
import com.cdts.demo.dagger.activity.DaggerFragmentComponent
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.data.cache.repository.CacheModel
import com.cdts.demo.data.cache.repository.CacheType
import com.cdts.demo.data.cache.viewmodel.CacheViewModel
import com.cdts.demo.schema.view.BaseListFragment
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import kotlinx.android.synthetic.main.fragment_list_with_toolbar.*
import javax.inject.Inject


class CacheFragment: BaseListFragment() {
    @Inject
    lateinit var mViewModel: CacheViewModel
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
        mViewModel.type = CacheType.valueOf(type ?: CacheType.Cache.name)
    }

    override fun loadData() {
        super.loadData()
        fetchData()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v: View
        val viewHolder: ViewHolder
        val data = getItem(position) as CacheModel
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
        if (data.detail is Double) {
            viewHolder.lblDetail.text = String.format("%.1f", data.detail)
        }
        return v
    }

    override fun itemClickEvent(listView: ListView, view: View, position: Int, id: Long) {
        val data = getItem(position) as CacheModel
        val type = CacheType.valueOf(type!!)
        when(data.title) {
            "String" -> mViewModel.saveString(type)
            "Int" -> mViewModel.saveInt(type)
            "Double" -> mViewModel.saveDouble(type)
            "Boolean" -> mViewModel.saveBool(type)
        }
        mViewModel.dataArray.value!![position] = mViewModel.updateData(type, data.title)!!
        listView.adapter.getView(position, listView.getChildAt(position - listView.firstVisiblePosition), listView)
    }

    class ViewHolder(itemView: View) {
        var lblTitle: TextView = itemView.findViewById(R.id.lblTitle) as TextView
        var lblDetail: TextView = itemView.findViewById(R.id.lblDetail) as TextView
    }
}