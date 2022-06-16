package ru.inteam.touristo.common_data

import kotlinx.coroutines.delay

internal class CoroutineDataStorageTestData {

    val expectedResultWithoutParameters = Result(1, 0)

    suspend fun source(): Result {
        delay(100)
        return expectedResultWithoutParameters
    }

    suspend fun source(a: Int, b: Int): Result {
        delay(100)
        return Result(a, b)
    }
}

internal data class Result(val a: Int, val b: Int)
