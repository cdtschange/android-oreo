package com.cdts.demo.data.network.view

import android.Manifest
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.cdts.demo.R
import com.cdts.demo.dagger.activity.DaggerFragmentComponent
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.data.cache.repository.CacheModel
import com.cdts.demo.data.network.repository.NetworkModel
import com.cdts.demo.data.network.repository.NetworkType
import com.cdts.demo.data.network.viewmodel.NetworkViewModel
import com.cdts.demo.schema.view.BaseListFragment
import com.cdts.demo.ui.application.MyApplication
import com.cdts.oreo.data.permission.ORPermission
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import kotlinx.android.synthetic.main.fragment_list_with_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class NetworkFragment: BaseListFragment() {
    @Inject
    lateinit var mViewModel: NetworkViewModel
    override var viewModel: ORBaseViewModel = ORBaseViewModel()
        get() = mViewModel

    override var listViewType: ListViewType = ListViewType.RefreshOnly

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
        mViewModel.type = NetworkType.valueOf(type ?: NetworkType.Get.name)
    }

    override fun loadData() {
        super.loadData()
        GlobalScope.launch(Dispatchers.Main) {
            delay(200)
            ORPermission.guard(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) { success ->
                if (success) {
                    autoRefresh()
                }
            }
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v: View
        val viewHolder: ViewHolder
        val data = getItem(position) as NetworkModel
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
        viewHolder.imgArrow.visibility = View.GONE
        return v
    }

    class ViewHolder(itemView: View) {
        var lblTitle: TextView = itemView.findViewById(R.id.lblTitle) as TextView
        var lblDetail: TextView = itemView.findViewById(R.id.lblDetail) as TextView
        var imgArrow: ImageView = itemView.findViewById(R.id.imgArrow) as ImageView
    }
}