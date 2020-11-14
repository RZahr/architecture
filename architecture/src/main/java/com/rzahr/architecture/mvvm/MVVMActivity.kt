package com.rzahr.architecture.mvvm

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@Suppress("unused")
abstract class MVVMActivity: AppCompatActivity() {// custom coroutine created}, CoroutineScope {

    //for custom coroutine private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       //for custom coroutine job = Job()
    }

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

    //for custom coroutine override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    //Dispatchers.IO: for network requests or disk read / write
    //Dispatchers.Main: for main safety
    //Dispathcers.Default: CPU intensive task
}