package ru.inteam.touristo.common.data.remote.presentation

sealed class CoroutineDataEvent {
    object Update : CoroutineDataEvent()

    class Failed(val e: Exception) : CoroutineDataEvent()
    class Updated<T>(val data: T) : CoroutineDataEvent()
}
