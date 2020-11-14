package com.rzahr.architecture.mvvm

import androidx.lifecycle.*

@Suppress("MemberVisibilityCanBePrivate")
abstract class MVVMViewModel<M:MVVMModel>: ViewModel(), LifecycleObserver {

    lateinit var model: M

    protected abstract fun onViewModelInject()

    init {
        this.onViewModelInject()
    }
}