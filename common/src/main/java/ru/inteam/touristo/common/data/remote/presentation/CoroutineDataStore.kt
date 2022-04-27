package ru.inteam.touristo.common.data.remote.presentation

import ru.inteam.touristo.common.data.remote.presentation.actors.LoadActor
import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.store.factory.TeaStoreFactory

typealias CoroutineDataStore<T> = Store<CoroutineDataState<T>, CoroutineDataEvent, Nothing>

class CoroutineDataStoreFactory<T>(
    source: suspend () -> T
) : TeaStoreFactory<CoroutineDataState<T>, CoroutineDataEvent, Nothing, CoroutineDataOperation>(
    initialState = CoroutineDataState(),
    actors = listOf(LoadActor(source)),
    reducer = CoroutineDataReducer()
)
