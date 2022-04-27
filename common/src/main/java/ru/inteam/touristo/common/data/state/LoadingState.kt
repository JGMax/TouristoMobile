package ru.inteam.touristo.common.data.state

sealed class LoadingState<T> {
    sealed class Loading<T> : LoadingState<T>() {
        class Default<T> : Loading<T>()

        data class WithData<T>(val content: T) : Loading<T>()
        class WithException<T>(val e: Exception) : Loading<T>()
    }

    data class Loaded<T>(val content: T) : LoadingState<T>()

    sealed class Failed<T>(val e: Exception) : LoadingState<T>() {
        class Default<T>(e: Exception) : Failed<T>(e)
        class WithData<T>(e: Exception, val content: T) : Failed<T>(e)
    }
}

val <T> LoadingState<T>.data: T?
    get() = when (this) {
        is LoadingState.Loaded<T> -> content
        is LoadingState.Loading.WithData<T> -> content
        is LoadingState.Failed.WithData<T> -> content
        else -> null
    }

val LoadingState<*>.exception: Exception?
    get() = when (this) {
        is LoadingState.Failed<*> -> e
        else -> null
    }
