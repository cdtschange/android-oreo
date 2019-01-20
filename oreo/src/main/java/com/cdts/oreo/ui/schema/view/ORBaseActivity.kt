package com.cdts.oreo.ui.schema.view

import android.annotation.SuppressLint
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.cdts.oreo.data.model.SerializableMap
import com.cdts.oreo.data.network.ORError
import com.cdts.oreo.extension.addClickAction
import com.cdts.oreo.ui.router.ORRouter
import com.cdts.oreo.ui.router.ORRouterProtocol
import com.cdts.oreo.ui.schema.lifecycle.ORActivityLifecycleObserver
import com.cdts.oreo.ui.schema.viewmodel.ORBaseViewModel
import com.cdts.oreo.ui.view.indicator.ORIndicator
import com.cdts.oreo.ui.view.indicator.ORIndicatorProtocol
import com.cdts.oreo.ui.view.toolbar.ORToolBar
import com.readystatesoftware.systembartint.SystemBarTintManager
import io.reactivex.Observable
import timber.log.Timber
import java.lang.reflect.Field

abstract class ORBaseActivity: AppCompatActivity(), ORRouterProtocol {

    interface OnResultObserverListener {
        fun invoke(requestCode: Int, resultCode: Int, data: Intent?)
    }

    companion object {

    }

    abstract var layoutResID: Int
    abstract var titleBar: ORToolBar?
    open lateinit var indicator: ORIndicatorProtocol
    open lateinit var viewModel: ORBaseViewModel
    open var canBackPress: Boolean = true
    private lateinit var lifecycleObserver: ORActivityLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ORRouter.push(this)

        lifecycleObserver = ORActivityLifecycleObserver(this::class.java.name)
        lifecycle.addObserver(lifecycleObserver)
        val bundle = intent.extras
        setParamsFromBundle(bundle)
        var binding: ViewDataBinding? = null
        if (layoutResID != 0) {
            try {
                binding = DataBindingUtil.setContentView(this, layoutResID)
                binding?.setLifecycleOwner(this)
            }catch (e: Exception){
                setContentView(layoutResID)
            }
        }
        setupDagger()
        setupBinding(binding)
        setupNavigation()
        setupUI()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        val bundle = getIntent().extras
        setParamsFromBundle(bundle)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifecycleObserver.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (!canBackPress) {
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        ORRouter.pop(this)
        Timber.e("Destroy: $this")
    }

    private fun setParamsFromBundle(bundle: Bundle?) {
        if (bundle != null && bundle.containsKey("params")) {
            try {
                val map = bundle.get("params") as SerializableMap
                setParams(map)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
    override fun setParams(map: SerializableMap) {
        val params = map.map?.entries
        if (params != null) {
            for ((key, value) in params) {
                try {
                    val field: Field? = try {
                        this.javaClass.getDeclaredField(key)
                    } catch (e: Exception) {
                        this.javaClass.getField(key)
                    }
                    field?.let { property ->
                        val type = property.type.name
                        if (!property.isAccessible) {
                            property.isAccessible = true
                        }
                        when (type) {
                            "java.lang.String" -> property.set(this, value)
                            "java.lang.Integer" -> property.set(this, Integer.parseInt(value.toString()))
                            "int" -> property.setInt(this, Integer.parseInt(value.toString()))
                            "java.lang.Boolean" -> property.set(this, java.lang.Boolean.parseBoolean(value.toString()))
                            "boolean" -> property.setBoolean(this, java.lang.Boolean.parseBoolean(value.toString()))
                            "java.util.List" -> property.set(this, value)
                            else -> property.set(this, value)
                        }
                    }
                } catch (e: Exception) {
                    e.stackTrace
                    Timber.e(e)
                }
            }
        }
    }

    open fun setupDagger() {}

    open fun setupBinding(binding: ViewDataBinding?) {
    }

    open fun setupNavigation() {
        titleBar?.let {
            setSupportActionBar(it.mToolbar)
            supportActionBar?.let { actionBar ->
                actionBar.setDisplayShowTitleEnabled(false)
            }
            it.mToolbar.setNavigationOnClickListener { finish() }
        }
    }
    open fun setupUI() {}

    open fun loadData() {}

    open fun showIndicator() {
        indicator.show(this, null)
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
        (indicator as ORIndicator).showTip(this, tip, 2, completion)
    }

    open fun showIndicatorObserver(): Observable<Unit> {
        return Observable.create { sink ->
            showIndicator()
            sink.onNext(Unit)
        }
    }


    open fun setupLeftBackAction(btn: View, action: () -> Unit = { onBackPressed() } ) {
        btn.addClickAction {
            action()
        }
    }
    /**
    // 调用此方法，当前activity将变成translucent主题
    // content区域包含全屏
    // statusBarRes用来设置状态栏颜色值
    // isFitSystemWindows设置是否需要让xml布局内容位于状态栏之下，默认是在状态栏下面
     */
    @SuppressLint("ObsoleteSdkInt")
    open fun setupStatusBar(@ColorRes statusBarRes: Int, isFitSystemWindows: Boolean = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            SystemBarTintManager(this).apply {
                isStatusBarTintEnabled = true
                setStatusBarTintResource(statusBarRes)
            }
        }
        if (isFitSystemWindows) {
            val contentFrameLayout = findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
            val parentView = contentFrameLayout.getChildAt(0)
            if (parentView != null && Build.VERSION.SDK_INT >= 14) {
                parentView.fitsSystemWindows = true
            }
        }
    }
    fun fragmentReplace(viewId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(viewId, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun fragmentShowPage(viewId: Int, fragment: Fragment) {
        fragmentReplace(viewId, fragment)
    }

    fun fragmentAdd(viewId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(viewId, fragment)
            .commitAllowingStateLoss()
    }

    fun fragmentPop() {
        supportFragmentManager.popBackStack()
    }

    override fun routedBackEvent(from: String, result: Boolean, params: Map<String, Any>) {}


    fun topMostActivity(): AppCompatActivity {
        return ORRouter.topActivity()
    }

    fun addResultObserver(observer: OnResultObserverListener) {
        lifecycleObserver.addResultObserver(observer)
    }

    fun removeResultObserver(observer: OnResultObserverListener) {
        lifecycleObserver.removeResultObserver(observer)
    }

}