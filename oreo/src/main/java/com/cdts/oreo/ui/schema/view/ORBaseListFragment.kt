package com.cdts.oreo.ui.schema.view

import `in`.srain.cube.views.ptr.*
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.database.DataSetObserver
import android.databinding.ViewDataBinding
import android.support.v4.content.ContextCompat
import android.support.v4.widget.TextViewCompat
import android.view.View
import android.widget.*
import com.cdts.oreo.R
import com.cdts.oreo.ui.schema.viewmodel.ORBaseListViewModel
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel

abstract class ORBaseListFragment : ORBaseFragment(), PtrHandler2, ListAdapter {

    enum class ListViewType {
        None, RefreshOnly, LoadMoreOnly, Both
    }

    override val layoutResID: Int = R.layout.fragment_or_list

    open val listViewModel: ORBaseListViewModel
        get() = viewModel as ORBaseListViewModel

    open var list: ListView? = null
    open var listViewContainer: PtrClassicFrameLayout? = null
    open var listViewType: ListViewType = ListViewType.RefreshOnly


    override fun setupUI() {
        super.setupUI()
        if (list == null) {
            list = if (rootView is ListView) {
                rootView as ListView
            } else {
                rootView?.findViewById(android.R.id.list)
            }
        }
        listViewContainer = listViewContainer ?: rootView?.findViewById(R.id.listViewContainer)

        list?.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
            itemClickEvent(parent as ListView, v, position, id)
        }
        listViewContainer?.setPtrHandler(this)
        listViewContainer?.isForceBackWhenComplete = true
        list?.adapter = this
    }

    override fun setupBinding(binding: ViewDataBinding?) {
        super.setupBinding(binding)
        listViewModel.dataArray.observe(this, Observer {
            listViewContainer?.refreshComplete()
        })
    }

    @SuppressLint("CheckResult")
    open fun fetchData() {
        willFetchListData()
        listViewModel.fetchData().subscribe({ data ->
            didFetchListData(data)
        }, { error ->
            didFetchListDataFailed(error)
        })
    }

    open fun willFetchListData() {

    }
    open fun didFetchListData(data: Any) {

    }
    open fun didFetchListDataFailed(error: Throwable) {
        (activity as ORBaseActivity).showTip(error)
    }
    open fun itemClickEvent(listView: ListView, view: View, position: Int, id: Long) {}

    fun autoRefresh(delay: Long = 100) {
        listViewContainer?.postDelayed({ listViewContainer?.autoRefresh() }, delay)
    }
    fun autoLoadMore(delay: Long = 100) {
        listViewContainer?.postDelayed({ listViewContainer?.autoLoadMore() }, delay)
    }

    final override fun onLoadMoreBegin(frame: PtrFrameLayout) {
        listViewModel.dataIndex += 1
        fetchData()
    }
    final override fun checkCanDoLoadMore(frame: PtrFrameLayout, content: View, footer: View): Boolean {
        return (listViewType == ListViewType.LoadMoreOnly || listViewType == ListViewType.Both)
                && listViewModel.canLoadMoreData
                && PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer)
    }
    final override fun onRefreshBegin(frame: PtrFrameLayout) {
        listViewModel.dataIndex = 0
        fetchData()
    }
    final override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
        return (listViewType == ListViewType.RefreshOnly || listViewType == ListViewType.Both)
                && PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header)
    }

    override fun areAllItemsEnabled(): Boolean {
        return true
    }
    override fun isEnabled(position: Int): Boolean {
        return true
    }

    override fun registerDataSetObserver(observer: DataSetObserver) {
        listViewModel.dataSetObservable.registerObserver(observer)
    }
    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        listViewModel.dataSetObservable.unregisterObserver(observer)
    }
    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getCount(): Int {
        return listViewModel.dataArray.value?.size ?: 0
    }
    override fun getItem(position: Int): Any {
        return listViewModel.dataArray.value!![position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItemViewType(position: Int): Int {
        return 0
    }
    override fun getViewTypeCount(): Int {
        return 1
    }
    override fun isEmpty(): Boolean {
        return count == 0
    }
}
