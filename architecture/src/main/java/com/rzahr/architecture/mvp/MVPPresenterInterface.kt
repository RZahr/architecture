package com.rzahr.architecture.mvp

import android.os.Bundle
import androidx.lifecycle.Lifecycle

/**
 * @author Rashad Zahr
 *
 * base presenter interface
 */
interface MVPPresenterInterface<V : MVPViewInterface> {

    fun attachView(view: V, activity: MVPActivity<*>)
    fun detachView()
    fun attachLifecycle(lifecycle: Lifecycle)
    fun onPresenterCreated()
    fun detachLifecycle(lifecycle: Lifecycle)
    fun onPresenterDestroy()
    fun getStateBundle(): Bundle?
}