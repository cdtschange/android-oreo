package com.cdts.oreo.ui.schema.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cdts.oreo.data.network.ORError
import com.cdts.oreo.ui.router.ORRouter
import com.cdts.oreo.ui.schema.lifecycle.ORActivityLifecycleObserver
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import io.reactivex.Observable
import timber.log.Timber


abstract class ORBaseFragment : Fragment() {

    abstract var layoutResID: Int
    abstract var titleBar: ORToolBar?
    open lateinit var indicator: ORIndicatorProtocol
    open lateinit var viewModel: ORBaseViewModel

    var rootView: View? = null
    private lateinit var lifecycleObserver: ORActivityLifecycleObserver
    var binding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleObserver = ORActivityLifecycleObserver(this::class.java.name)
        lifecycle.addObserver(lifecycleObserver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (layoutResID != 0) {
            try {
                binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
                binding?.setLifecycleOwner(this)
                rootView = if(binding != null) binding?.root else inflater.inflate(layoutResID, container, false)
            } catch (e: Exception){
                rootView = inflater.inflate(layoutResID, container, false)
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDagger()
        setupBinding(binding)
        setupNavigation()
        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("Destroy: $this")
    }

    open fun setupDagger() {}

    open fun setupBinding(binding: ViewDataBinding?) {}

    open fun setupNavigation() {
        titleBar?.let {
            (activity as? AppCompatActivity)?.setSupportActionBar(it.mToolbar)
            (activity as? AppCompatActivity)?.supportActionBar?.let { actionBar ->
                actionBar.setDisplayShowTitleEnabled(false)
            }
            it.mToolbar.setNavigationOnClickListener { activity?.finish() }
        }
    }

    open fun setupUI() {}

    open fun loadData() {}

    open fun showIndicator() {
        indicator.show(activity, null, null)
    }
    open fun hideIndicator() {
        indicator.hide()
    }
    open fun showTip(error: Throwable, completion: () -> Unit = {}) {
        when(error) {
            is ORError -> showTip(error.message, completion)
            else -> showTip(error.message ?: error.toString(), completion)
        }
    }
    open fun showTip(tip: String, completion: () -> Unit = {}) {
        (indicator as ORIndicator).showTip(activity, tip, 2, completion)
    }

    open fun showIndicatorObserver(): Observable<Unit> {
        return Observable.create { sink ->
            showIndicator()
            sink.onNext(Unit)
        }
    }

    /**
     * activity里嵌套fragment不能使用以下fragment方法
     */
    fun Fragment.fragmentReplace(viewId: Int, fragment: Fragment) {
        childFragmentManager
                .beginTransaction()
                .replace(viewId, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    fun Fragment.fragmentShowPage(viewId: Int, fragment: Fragment) {
        fragmentReplace(viewId, fragment)
    }

    fun Fragment.fragmentAdd(viewId: Int, fragment: Fragment) {
        childFragmentManager
                .beginTransaction()
                .add(viewId, fragment)
                .commitAllowingStateLoss()
    }

    fun Fragment.fragmentPop() {
        childFragmentManager.popBackStack()
    }

}