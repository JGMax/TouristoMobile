package ru.inteam.touristo.common.data.remote.presentation

sealed class CoroutineDataOperation {
    object Update : CoroutineDataOperation()
}
