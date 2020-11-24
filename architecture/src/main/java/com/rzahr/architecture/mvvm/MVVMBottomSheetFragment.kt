package com.rzahr.architecture.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@Suppress("unused")
abstract class MVVMBottomSheetFragment(private val layoutId: Int): BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(layoutId, container, false)
    }

    fun <T> MutableLiveData<T>.observeOn(observer: Observer<T>) {
        @Suppress("UNCHECKED_CAST")
        observe(viewLifecycleOwner, observer)
    }

    fun <T> MutableLiveData<T>.observe(completion: (it: T) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        observe(viewLifecycleOwner, {
            completion(it)
        })
    }

    fun <T> LiveData<T>.observeOn(observer: Observer<T>) {
        @Suppress("UNCHECKED_CAST")
        observe(viewLifecycleOwner, observer)
    }

    fun <T> LiveData<T>.observe(completion: (it: T) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        observe(viewLifecycleOwner, {
            completion(it)
        })
    }
}