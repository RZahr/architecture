package com.rzahr.architecture.mvvm

import android.os.Bundle
import android.view.View.inflate
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.rzahr.architecture.statusBarAdapt

@Suppress("unused")
abstract class MVVMActivity<b: ViewBinding>: AppCompatActivity() {// custom coroutine created}, CoroutineScope {

    //for custom coroutine private lateinit var job: Job

    lateinit var binding: b

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarAdapt()
        onBindToView()
    }

    /**
     * @sample: binding = ActivityMainBinding.inflate(layoutInflater)
                val view = binding.root
                setContentView(view)
     */
    protected abstract fun onBindToView()


    override fun onDestroy() {
        super.onDestroy()
        //for custom coroutine  job.cancel()
    }

    fun <T> MutableLiveData<T>.observeOn(observer: Observer<T>) {
        @Suppress("UNCHECKED_CAST")
        observe(this@MVVMActivity, observer)
    }

    fun <T> MutableLiveData<T>.observe(completion: (it: T) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        observe(this@MVVMActivity, {
            completion(it)
        })
    }


    fun <T> LiveData<T>.observeOn(observer: Observer<T>) {
        @Suppress("UNCHECKED_CAST")
        observe(this@MVVMActivity, observer)
    }

    fun <T> LiveData<T>.observe(completion: (it: T) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        observe(this@MVVMActivity, {
            completion(it)
        })
    }


    //example on using observe:
    /*viewModel.mutable1.observe { state ->

            when (state) {
                is LiveDataState.Success -> {
                    test_Tv?.text = state.response.resultString
                }
                is LiveDataState.Failure -> {
                }
                is LiveDataState.Loading -> {
                    test_Tv?.text = "Loading..."
                }
            }
        }*/

    //for custom coroutine override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    //Dispatchers.IO: for network requests or disk read / write
    //Dispatchers.Main: for main safety
    //Dispathcers.Default: CPU intensive task
}