package ru.inteam.touristo.common.tea

import kotlinx.coroutines.flow.Flow

interface Actor<in Operation : Any, out Event : Any> {
    fun process(operations: Flow<Operation>): Flow<Event>
}
