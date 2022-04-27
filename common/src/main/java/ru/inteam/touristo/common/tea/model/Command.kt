package ru.inteam.touristo.common.tea.model

data class Command<State : Any, Operation : Any, Action : Any>(
    val state: State?,
    val operations: List<Operation> = emptyList(),
    val actions: List<Action> = emptyList()
)
