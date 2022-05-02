package ru.inteam.touristo.common_data

import kotlinx.coroutines.flow.Flow
import ru.inteam.touristo.common_data.state.LoadingState

interface DataStorage<T> {
    fun data(vararg params: Pair<String, Any>): Flow<LoadingState<T>>
}
