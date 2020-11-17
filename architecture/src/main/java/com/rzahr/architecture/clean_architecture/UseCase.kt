package com.rzahr.architecture.clean_architecture

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {

    var requestValues: Q? = null

    var useCaseCallback: UseCaseCallback<P>? = null

    internal fun run() {
        execute(requestValues)
    }

    internal fun onClear() {
        clearUseCase()
    }

    protected abstract fun clearUseCase()

    protected abstract fun execute(requestValues: Q?)

    /**
     * Data passed to a request.
     */
    interface RequestValues

    /**
     * Data received from a request.
     */
    interface ResponseValue

    interface UseCaseCallback<R> {
        fun onSuccess(response: R)
        fun onError(t: Throwable)
    }
}