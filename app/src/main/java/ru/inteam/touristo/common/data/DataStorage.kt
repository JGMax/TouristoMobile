package ru.inteam.touristo.common.data

import kotlinx.coroutines.flow.Flow
import ru.inteam.touristo.common.data.state.LoadingState

interface DataStorage<T> {
    fun data(vararg params: Pair<String, Any>): Flow<LoadingState<T>>
}
