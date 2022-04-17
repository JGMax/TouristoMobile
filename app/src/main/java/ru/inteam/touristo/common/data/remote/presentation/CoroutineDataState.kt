package ru.inteam.touristo.common.data.remote.presentation

import ru.inteam.touristo.common.data.state.LoadingState

data class CoroutineDataState<T>(
    val loadingState: LoadingState<T> = LoadingState.Loading.Default()
)
