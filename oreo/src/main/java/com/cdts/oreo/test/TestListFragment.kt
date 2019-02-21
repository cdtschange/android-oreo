package com.cdts.oreo.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.cdts.oreo.R
import com.cdts.oreo.ui.schema.repository.ORBaseRepository
import com.cdts.oreo.ui.schema.view.ORBaseListFragment
import com.cdts.oreo.ui.schema.viewmodel.ORBaseListViewModel
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import io.reactivex.Observable
import kotlinx.android.synthetic.main.test_fragment_empty.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestListFragment : ORBaseListFragment() {

    override val layoutResID: Int = R.layout.test_fragment_list

    override val titleBar: ORToolBar?
        get() = testToolBar

    override var viewModel: ORBaseViewModel = TestListViewModel()

    override var listViewType: ListViewType = ListViewType.Both

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    override fun loadData() {
        super.loadData()
        autoRefresh()
        GlobalScope.launch(Dispatchers.Default) {
            delay(1000)
            (viewModel as TestListViewModel).dataIndex += 1
            autoLoadMore()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v: View
        val viewHolder: ViewHolder
        val data = getItem(position) as String
        if (convertView == null) {
            v = LayoutInflater.from(parent?.context).inflate(R.layout.test_list_view_item, parent, false)
            viewHolder = ViewHolder(v)
            v.tag = viewHolder
        } else {
            v = convertView
            viewHolder = v.tag as ViewHolder
        }
        viewHolder.lblTitle.text = data
        return v
    }

    override fun itemClickEvent(listView: ListView, view: View, position: Int, id: Long) {
    }

    class ViewHolder(itemView: View) {
        var lblTitle: TextView = itemView.findViewById(R.id.lblTitle) as TextView
    }
}


class TestListViewModel: ORBaseListViewModel() {

    override var repository: ORBaseRepository = TestListRepository()

    override fun fetchData(): Observable<Any> {
        return Observable.just((repository as TestListRepository).fetchItems(dataIndex, listLoadNumber)).map { data ->
            appendDataArray(data)
            data
        }
    }
}


class TestListRepository: ORBaseRepository() {

    fun fetchItems(start: Int, size: Int): List<String> {
        val data = mutableListOf<String>()
        val count = if (start == 0) size else size / 2
        for (i in 0..count) {
            data.add(i.toString())
        }
        return data
    }
}
