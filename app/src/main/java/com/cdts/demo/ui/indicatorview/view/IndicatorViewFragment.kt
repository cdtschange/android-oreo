package com.cdts.demo.ui.indicatorview.view

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.cdts.demo.R
import com.cdts.demo.dagger.activity.DaggerFragmentComponent
import com.cdts.demo.dagger.activity.module.FragmentModule
import com.cdts.demo.schema.view.BaseListFragment
import com.cdts.demo.tab.repository.MenuModel
import com.cdts.demo.ui.application.MyApplication
import com.cdts.demo.ui.indicatorview.viewmodel.IndicatorViewModel
import com.cdts.demo.ui.listview.viewmodel.ListTypeViewModel
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.cdts.oreo.ui.view.indicator.ORIndicatorType
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list_with_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class IndicatorViewFragment: BaseListFragment() {
    @Inject
    lateinit var mViewModel: IndicatorViewModel
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

    override fun loadData() {
        super.loadData()
        fetchData()
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

    override fun itemClickEvent(listView: ListView, view: View, position: Int, id: Long) {
        when(position) {
            0 -> {
                showIndicator()
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    hideIndicator()
                }
            }
            1 -> {
                indicator.show(activity, "Loading...", null)
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    hideIndicator()
                }
            }
            2 -> {
                indicator.show(activity, "Loading...", "Parsing data (1/1)")
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    hideIndicator()
                }
            }
            3 -> {
                (indicator as ORIndicator).showProgress(activity, "Loading...", "Parsing data (0/100)", false, null, ORIndicatorType.PieDeterminate)
                var progress = 0
                var disposable: Disposable? = null
                disposable = Observable.interval(40, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        progress += 1
                        if (progress > 100) {
                            disposable?.dispose()
                            hideIndicator()
                            return@subscribe
                        }
                        (indicator as ORIndicator).changeProgress(null, "Parsing data ($progress/100)", progress)
                    }
            }
            4 -> {
                (indicator as ORIndicator).showProgress(activity, "Loading...", "Parsing data (0/100)", false, null, ORIndicatorType.AnnularDeterminate)
                var progress = 0
                var disposable: Disposable? = null
                disposable = Observable.interval(40, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        progress += 1
                        if (progress > 100) {
                            disposable?.dispose()
                            hideIndicator()
                            return@subscribe
                        }
                        (indicator as ORIndicator).changeProgress(null, "Parsing data ($progress/100)", progress)
                    }
            }
            5 -> {
                (indicator as ORIndicator).showProgress(activity, "Loading...", "Parsing data (0/100)", false, null, ORIndicatorType.BarDeterminate)
                var progress = 0
                var disposable: Disposable? = null
                disposable = Observable.interval(40, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        progress += 1
                        if (progress > 100) {
                            disposable?.dispose()
                            hideIndicator()
                            return@subscribe
                        }
                        (indicator as ORIndicator).changeProgress(null, "Parsing data ($progress/100)", progress)
                    }
            }
            6 -> {
                var disposable: Disposable? = null
                var cancelHandler: (() -> Unit)? = {
                    disposable?.dispose()
                }
                (indicator as ORIndicator).showProgress(activity, "Loading...", "Parsing data (0/100)", true, cancelHandler, ORIndicatorType.PieDeterminate)
                var progress = 0
                disposable = Observable.interval(100, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        progress += 1
                        if (progress > 100) {
                            disposable?.dispose()
                            hideIndicator()
                            return@subscribe
                        }
                        (indicator as ORIndicator).changeProgress(null, "Parsing data ($progress/100)", progress)
                    }
            }
            7 -> {
                showTip("This is a message")
            }
            8 -> {
                (indicator as ORIndicator).showTip(activity, "This is a bottom message", null, null, false, 2)
            }
            9 -> {
                (indicator as ORIndicator).showTip(activity, "This is a custom message", null, R.drawable.ic_hud_checkmark, true, 2)
            }
        }
    }

    class ViewHolder(itemView: View) {
        var lblTitle: TextView = itemView.findViewById(R.id.lblTitle) as TextView
        var lblDetail: TextView = itemView.findViewById(R.id.lblDetail) as TextView
        var imgArrow: ImageView = itemView.findViewById(R.id.imgArrow) as ImageView
    }
}