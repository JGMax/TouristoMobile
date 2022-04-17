package ru.inteam.touristo.common.tea

import ru.inteam.touristo.common.tea.model.Command

abstract class Reducer<State : Any, Event : Any, Action : Any, Operation : Any> {
    lateinit var state: State
    private val operations = mutableListOf<Operation>()
    private val actions = mutableListOf<Action>()

    protected abstract fun reduce(event: Event)

    fun reduce(state: State, event: Event): Command<State, Operation, Action> {
        this.state = state
        reduce(event)
        val cmd = build()
        clear()
        return cmd
    }

    protected fun state(block: State.() -> State) {
        state = block(state)
    }

    protected fun operations(vararg operations: Operation) {
        this.operations.addAll(operations)
    }

    protected fun actions(vararg actions: Action) {
        this.actions.addAll(actions)
    }

    private fun clear() {
        operations.clear()
        actions.clear()
    }

    private fun build(): Command<State, Operation, Action> {
        return Command(state, operations.toList(), actions.toList())
    }
}
