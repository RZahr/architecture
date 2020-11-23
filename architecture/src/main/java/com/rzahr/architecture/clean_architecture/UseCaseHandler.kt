package com.rzahr.architecture.clean_architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UseCaseHandler() {

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(useCase: UseCase<T, R>, values: T, callback: UseCase.UseCaseCallback<R>) {

        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackWrapper(callback, this)
        useCase.useCaseCallback?.onLoading()
        useCase.run()
    }


    /*example
    *
    * useCaseHandler.execute(getBundles, GetBundlesUseCase.RequestValues(),
                object : UseCase.UseCaseCallback<GetBundlesUseCase.ResponseValue> {

                    override fun onSuccess(response: GetBundlesUseCase.ResponseValue) {
                        bundlesLiveData as MutableLiveData
                        bundlesLiveData.value = response.bundles
                    }

                    override fun onError(t: Throwable) {
                        bundlesLiveData as MutableLiveData
                        bundlesLiveData.value = null
                    }
                })
    * */

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> executeWithLiveData(UseCaseN: UseCase<T, R>, values: T, liveData: LiveData<LiveDataState<R>>) {

        UseCaseN.requestValues = values
        UseCaseN.useCaseCallbackN = UiCallbackWrapperN(liveData)
        UseCaseN.useCaseCallbackN?.onLoading()
        UseCaseN.run()
    }
    /*example
    *
    *  useCaseHandler.executeWithLiveData(getMessages, requestValue, messagesLiveData)
    * */

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> finish(useCase: UseCase<T, R>) {
        useCase.onClear()
    }

    private fun <V : UseCase.ResponseValue> notifyResponse(response: V, useCaseCallback: UseCase.UseCaseCallback<V>) {
        useCaseCallback.onSuccess(response)
    }

    private fun <V : UseCase.ResponseValue> notifyError(useCaseCallback: UseCase.UseCaseCallback<V>, t: Throwable) {
        useCaseCallback.onError(t)
    }

    private fun <V : UseCase.ResponseValue> notifyLoading(useCaseCallback: UseCase.UseCaseCallback<V>) {
        useCaseCallback.onLoading()
    }

    private class UiCallbackWrapperN<V : UseCase.ResponseValue>(private val liveData: LiveData<LiveDataState<V>>) : UseCase.UseCaseCallbackN<V> {

        override fun onSuccess(response: V) {
            liveData as MutableLiveData
            liveData.value = LiveDataState.Success(response)
        }

        override fun onError(t: Throwable) {
            liveData as MutableLiveData
            liveData.value = LiveDataState.Failure(t)
        }

        override fun onLoading() {
            liveData as MutableLiveData
            liveData.value = LiveDataState.Loading
        }
    }

    private class UiCallbackWrapper<V : UseCase.ResponseValue>(private val mCallback: UseCase.UseCaseCallback<V>, private val mUseCaseHandler: UseCaseHandler) : UseCase.UseCaseCallback<V> {

        override fun onSuccess(response: V) {
            mUseCaseHandler.notifyResponse(response, mCallback)
        }

        override fun onError(t: Throwable) {
            mUseCaseHandler.notifyError(mCallback, t)
        }

        override fun onLoading() {
            mUseCaseHandler.notifyLoading(mCallback)
        }
    }

    companion object {

        private var INSTANCE: UseCaseHandler? = null

        fun getInstance(): UseCaseHandler {
            if (INSTANCE == null) INSTANCE = UseCaseHandler()

            return INSTANCE!!
        }
    }
}