package ru.inteam.touristo.common_data.state

import ru.inteam.touristo.common_data.state.LoadingState.Failed
import ru.inteam.touristo.common_data.state.LoadingState.Loading

sealed class LoadingState<T> {

    sealed class Loading<T> : LoadingState<T>() {
        class Default<T> : Loading<T>()

        data class WithData<T>(val content: T) : Loading<T>()
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
        is Loading.WithData<T> -> content
        is Failed.WithData<T> -> content
        else -> null
    }

val LoadingState<*>.exception: Exception?
    get() = when (this) {
        is Failed<*> -> e
        else -> null
    }

inline fun <T, R> LoadingState<T>.map(
    onLoading: (T?) -> R,
    onSuccess: (T) -> R,
    onError: (T?, Throwable) -> R
): R {
    return when (this) {
        is Loading.Default<T> -> onLoading(null)
        is Loading.WithData<T> -> onLoading(content)
        is LoadingState.Loaded<T> -> onSuccess(content)
        is Failed.Default<T> -> onError(null, e)
        is Failed.WithData<T> -> onError(content, e)
    }
}

inline fun <T, R> LoadingState<T>.map(
    onLoading: (T?) -> R,
    onSuccess: (T) -> R
): R {
    return when (this) {
        is Loading.Default<T> -> onLoading(null)
        is Loading.WithData<T> -> onLoading(content)
        is LoadingState.Loaded<T> -> onSuccess(content)
        is Failed.Default<T> -> onLoading(null)
        is Failed.WithData<T> -> onLoading(content)
    }
}

inline fun <T, R> LoadingState<T>.map(
    transform: (T) -> R
): LoadingState<R> {
    @Suppress("UNCHECKED_CAST")
    return when(this) {
        is LoadingState.Loaded<T> -> LoadingState.Loaded(transform(content))
        is Loading.WithData<T> -> Loading.WithData(transform(content))
        is Failed.WithData<T> -> Failed.WithData(e, transform(content))
        else -> this as LoadingState<R>
    }
}
