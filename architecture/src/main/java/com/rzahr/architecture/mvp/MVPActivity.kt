package com.rzahr.architecture.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.rzahr.architecture.statusBarAdapt
import com.rzahr.architecture.utils.MIN_GUARD_INTERVAL
import javax.inject.Inject

/**
 * @author Rashad Zahr
 *
 * base activity
 */
@Suppress("unused")
abstract class MVPActivity<b: ViewBinding, P : MVPPresenterInterface<*>>: AppCompatActivity(), MVPViewInterface {

    @Inject lateinit var mPresenter: P
    lateinit var binding: b
    private var presenter: MVPPresenter<*, *>? = null
    private var lastEventTime = System.currentTimeMillis()
    private var initialized = false

    protected abstract fun onPresenterShouldAttachView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarAdapt()
        onInitBinding()
        val view = binding.root
        setContentView(view)
        onPresenterShouldAttachView()
    }

    /**
     * @sample: binding = ActivityMainBinding.inflate(layoutInflater)
     */
    protected abstract fun onInitBinding()

    override fun setPresenter(presenter: MVPPresenter<*, *>) {

        this.presenter = presenter
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.detachView()
        presenter = null
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