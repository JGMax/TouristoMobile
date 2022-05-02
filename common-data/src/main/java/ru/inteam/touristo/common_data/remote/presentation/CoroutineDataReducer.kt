package ru.inteam.touristo.common_data.remote.presentation

import ru.inteam.touristo.common_data.state.LoadingState.*
import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.common_data.state.data
import ru.inteam.touristo.common_data.state.exception

internal class CoroutineDataReducer<T> :
    Reducer<CoroutineDataState<T>, CoroutineDataEvent, Nothing, CoroutineDataOperation>() {

    override fun reduce(event: CoroutineDataEvent) {
        @Suppress("UNCHECKED_CAST")
        when (event) {
            is CoroutineDataEvent.Update -> {
                operations(CoroutineDataOperation.Update)
                loadingState()
            }
            is CoroutineDataEvent.Updated<*> -> successState(event.data as T)
            is CoroutineDataEvent.Failed -> failedState(event.e)
        }
    }

    private fun failedState(e: Exception) {
        state {
            val data = loadingState.data
            if (data != null) {
                copy(loadingState = Failed.WithData(e, data))
            } else {
                copy(loadingState = Failed.Default(e))
            }
        }
    }

    private fun loadingState() {
        state {
            val data = loadingState.data
            val error = loadingState.exception
            when {
                data != null -> copy(loadingState = Loading.WithData(data))
                error != null -> copy(loadingState = Loading.WithException(error))
                else -> this
            }
        }
    }

    private fun successState(data: T) {
        state { copy(loadingState = Loaded(data)) }
    }
}
