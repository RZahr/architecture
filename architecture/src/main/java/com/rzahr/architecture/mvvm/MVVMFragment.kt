package com.rzahr.architecture.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

@Suppress("unused")
abstract class MVVMFragment(private val layoutId: Int): Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(layoutId, container, false)
    }

    fun <T> MutableLiveData<T>.observeOn(observer: Observer<T>) {
        @Suppress("UNCHECKED_CAST")
        observe(this@MVVMFragment, observer)
    }

    fun <T> MutableLiveData<T>.observe(completion: (it: T) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        observe(this@MVVMFragment, {
            completion(it)
        })
    }

    fun <T> LiveData<T>.observeOn(observer: Observer<T>) {
        @Suppress("UNCHECKED_CAST")
        observe(this@MVVMFragment, observer)
    }

    fun <T> LiveData<T>.observe(completion: (it: T) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        observe(this@MVVMFragment, {
            completion(it)
        })
    }
}