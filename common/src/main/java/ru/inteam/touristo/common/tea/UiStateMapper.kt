package ru.inteam.touristo.common.tea

fun interface UiStateMapper<in State : Any, out UiState : Any> {
    fun map(state: State): UiState
}
