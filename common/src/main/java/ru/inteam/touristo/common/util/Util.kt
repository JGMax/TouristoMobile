package ru.inteam.touristo.common.util

fun min(vararg nums: Int?): Int? {
    return nums.filterNotNull().minOrNull()
}
