package ru.inteam.touristo.data.remote.presentation

import ru.inteam.touristo.data.state.LoadingState

internal data class CoroutineDataState<T>(
    val loadingState: LoadingState<T> = LoadingState.Loading.Default()
)
