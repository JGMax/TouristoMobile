package ru.inteam.touristo.common.data.remote.presentation.actors

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.inteam.touristo.common.data.remote.presentation.CoroutineDataOperation
import ru.inteam.touristo.common.data.remote.presentation.CoroutineDataOperation.Update
import ru.inteam.touristo.common.data.remote.presentation.CoroutineDataEvent
import ru.inteam.touristo.common.data.remote.presentation.CoroutineDataEvent.Failed
import ru.inteam.touristo.common.data.remote.presentation.CoroutineDataEvent.Updated
import ru.inteam.touristo.common.tea.Actor

class LoadActor<T>(
    private val source: suspend () -> T
) : Actor<CoroutineDataOperation, CoroutineDataEvent> {
    override fun process(operations: Flow<CoroutineDataOperation>): Flow<CoroutineDataEvent> {
        return operations
            .filterIsInstance<Update>()
            .mapLatest<Update, CoroutineDataEvent> { Updated(source()) }
            .flowOn(Dispatchers.IO)
            .catch { emit(Failed(it as Exception)) }
    }
}
