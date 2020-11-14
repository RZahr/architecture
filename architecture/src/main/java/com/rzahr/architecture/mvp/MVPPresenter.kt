package com.rzahr.architecture.mvp

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.rzahr.architecture.utils.MIN_GUARD_INTERVAL
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * @author Rashad Zahr
 *
 * base presenter class
 */
@Suppress("unused")
open class MVPPresenter<V : MVPViewInterface, M: MVPModel> @Inject constructor(): MVPPresenterInterface<V>, LifecycleObserver {

    @Inject lateinit var model: M
    private var lastEventTime = System.currentTimeMillis()
    private var initialized = false
    private var stateBundle: Bundle? = null
    private val isViewAttached: Boolean get() = mViewWeakReference != null && mViewWeakReference?.get() != null
    private val isActivityAttached: Boolean get() = mActivityWeakReference != null && mActivityWeakReference?.get() != null

    val view: V? get() = mViewWeakReference?.get()
    val activity: MVPActivity<*>? get() = mActivityWeakReference?.get()

    override fun getStateBundle(): Bundle? {
        if (stateBundle == null) stateBundle = Bundle()
        return stateBundle
    }

    override fun onPresenterDestroy() {
        if (stateBundle != null && !stateBundle!!.isEmpty) stateBundle?.clear()
    }

    override fun detachLifecycle(lifecycle: Lifecycle) {
        lifecycle.removeObserver(this)
    }

    override fun attachLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    override fun onPresenterCreated() {
    }

    private var mViewWeakReference: WeakReference<V>? = null
    private var mActivityWeakReference: WeakReference<MVPActivity<*>>? = null

    override fun attachView(view: V, activity: MVPActivity<*>) {
        if (!isViewAttached) {
            mViewWeakReference = WeakReference(view)
            view.setPresenter(this)
        }
        if (!isActivityAttached) mActivityWeakReference = WeakReference(activity)
    }

    override fun detachView() {
        mViewWeakReference?.clear()
        mViewWeakReference = null
        mActivityWeakReference?.clear()
        mActivityWeakReference = null
    }

    fun guard(code: () -> Unit) {

        val eventTime = System.currentTimeMillis()

        if (eventTime - lastEventTime > MIN_GUARD_INTERVAL || !initialized) {
            initialized = true
            lastEventTime = eventTime
            code()
        }
    }
}