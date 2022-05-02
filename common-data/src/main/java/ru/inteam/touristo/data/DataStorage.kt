package ru.inteam.touristo.data

import kotlinx.coroutines.flow.Flow
import ru.inteam.touristo.data.state.LoadingState

interface DataStorage<T> {
    fun data(vararg params: Pair<String, Any>): Flow<LoadingState<T>>
}
