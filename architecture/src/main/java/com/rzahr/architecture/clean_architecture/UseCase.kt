package com.rzahr.architecture.clean_architecture

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {

    //var requestValues: Q? = null
    lateinit var requestValues: Q

    var useCaseCallback: UseCaseCallback<P>? = null
    var useCaseLiveDataCallback: UseCaseLiveDataCallback<P>? = null

    internal suspend fun run() = withContext(Dispatchers.IO) {
        execute(requestValues)
    }

    internal fun onClear() {
        clearUseCase()
    }

    protected abstract fun clearUseCase()

    protected abstract suspend fun execute(requestValues: Q)

    interface RequestValues

    interface ResponseValue

    interface UseCaseCallback<R> {
        suspend fun onSuccess(response: R)
        suspend fun onError(t: Throwable)
        suspend fun onLoading(message: String = "")
    }

    interface UseCaseLiveDataCallback<R> {
        suspend fun onSuccess(response: R)
        suspend fun onError(t: Throwable)
        suspend fun onLoading(message: String = "")
    }
}


//backup without coroutines
// abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue> {
//
//    //var requestValues: Q? = null
//    lateinit var requestValues: Q
//
//    var useCaseCallback: UseCaseCallback<P>? = null
//    var useCaseCallbackN: UseCaseCallbackN<P>? = null
//
//    internal fun run() {
//        execute(requestValues)
//    }
//
//    internal fun onClear() {
//        clearUseCase()
//    }
//
//    protected abstract fun clearUseCase()
//
//    protected abstract fun execute(requestValues: Q)
//  //  protected abstract fun execute(requestValues: Q?)
//
//    /**
//     * Data passed to a request.
//     */
//    interface RequestValues
//
//    /**
//     * Data received from a request.
//     */
//    interface ResponseValue
//
//    interface UseCaseCallback<R> {
//        fun onSuccess(response: R)
//        fun onError(t: Throwable)
//        fun onLoading()
//    }
//
//    interface UseCaseCallbackN<R> {
//        fun onSuccess(response: R)
//        fun onError(t: Throwable)
//        fun onLoading()
//    }
//}