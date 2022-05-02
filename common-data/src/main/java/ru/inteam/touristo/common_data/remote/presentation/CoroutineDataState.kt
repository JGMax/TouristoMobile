package ru.inteam.touristo.common_data.remote.presentation

import ru.inteam.touristo.common_data.state.LoadingState

internal data class CoroutineDataState<T>(
    val loadingState: LoadingState<T> = LoadingState.Loading.Default()
)
