package com.rzahr.architecture.clean_architecture

sealed class LiveDataState<out T> {
    data class Success<out T>(val response: T) : LiveDataState<T>()
    data class Failure(val error: Throwable) : LiveDataState<Nothing>()
    object Loading: LiveDataState<Nothing>()

    fun <T> result(fallback: T): T {
        @Suppress("UNCHECKED_CAST")
        return (this as? Success<T>)?.response ?: fallback
    }

    val success get() = this is Success<*> && response != null
}