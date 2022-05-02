package ru.inteam.touristo.data.remote.presentation

internal sealed class CoroutineDataEvent {
    object Update : CoroutineDataEvent()

    class Failed(val e: Exception) : CoroutineDataEvent()
    class Updated<T>(val data: T) : CoroutineDataEvent()
}
