package com.rzahr.architecture.mvvm

import androidx.lifecycle.*
import com.rzahr.architecture.clean_architecture.LiveDataState
import com.rzahr.architecture.clean_architecture.UseCase
import com.rzahr.architecture.clean_architecture.UseCaseHandler

open class MVVMViewModel constructor(useCaseHandler: UseCaseHandler) : ViewModel(), LifecycleObserver {

    private val mUseCaseHandler = useCaseHandler

    suspend fun <Q : UseCase.RequestValues, P : UseCase.ResponseValue> executeUseCaseWithLiveData(useCase: UseCase<Q, P>, requestValues: Q, liveData: LiveData<LiveDataState<P>>) {
        mUseCaseHandler.executeWithLiveData(useCase, requestValues, liveData)
    }

    suspend fun <Q : UseCase.RequestValues, P : UseCase.ResponseValue> executeUseCase(useCase: UseCase<Q, P>, requestValues: Q, callback: UseCase.UseCaseCallback<P>) {
        mUseCaseHandler.execute(useCase, requestValues, callback)
    }

    /*example:
    * class MainActivityViewModel2 @ViewModelInject constructor(private val firstUseCase: FirstUseCase,
                                                          useCaseHandler: UseCaseHandler,
                                                          @Assisted private val savedStateHandle: SavedStateHandle): MVVMViewModelN(useCaseHandler) {

    val mutable1 = savedStateHandle.getLiveData<LiveDataState<FirstUseCase.Response>>("mutable1")

    fun update() {
        viewModelScope.launch {
            executeUseCaseWithLiveData(firstUseCase, FirstUseCase.Request("123"), mutable1)

            /*executeUseCase(firstUseCase, FirstUseCase.Request("123"), object: UseCase.UseCaseCallback<FirstUseCase.Response>{
            override suspend fun onSuccess(response: FirstUseCase.Response) {
                mutableText2 as MutableLiveData
                mutableText2.value = LiveDataState.Success(response)
            }

            override suspend fun onError(t: Throwable) {
            }

            override suspend fun onLoading() {
                mutableText2 as MutableLiveData
                mutableText2.value = LiveDataState.Loading
            }
        })*/
        }
    }
}*/
}