package com.rzahr.architecture.clean_architecture

class UseCaseHandler() {

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(useCase: UseCase<T, R>, values: T, callback: UseCase.UseCaseCallback<R>) {

        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackWrapper(callback, this)
        useCase.run()
    }

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> finish(useCase: UseCase<T, R>) {
        useCase.destroy()
    }

    private fun <V : UseCase.ResponseValue> notifyResponse(response: V, useCaseCallback: UseCase.UseCaseCallback<V>) {
        useCaseCallback.onSuccess(response)
    }

    private fun <V : UseCase.ResponseValue> notifyError(useCaseCallback: UseCase.UseCaseCallback<V>, t: Throwable) {
        useCaseCallback.onError(t)
    }

    private class UiCallbackWrapper<V : UseCase.ResponseValue>(private val mCallback: UseCase.UseCaseCallback<V>, private val mUseCaseHandler: UseCaseHandler) : UseCase.UseCaseCallback<V> {

        override fun onSuccess(response: V) {
            mUseCaseHandler.notifyResponse(response, mCallback)
        }


        override fun onError(t: Throwable) {
            mUseCaseHandler.notifyError(mCallback, t)
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