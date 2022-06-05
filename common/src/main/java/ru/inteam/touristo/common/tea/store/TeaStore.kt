package ru.inteam.touristo.common.tea.store

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.inteam.touristo.common.tea.Actor
import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.UiStateMapper

class TeaStore<State : Any, Event : Any, Action : Any, Operation : Any> internal constructor(
    initialState: State,
    private val reducer: Reducer<State, Event, Action, Operation>,
    private val actors: Store<State, Event, Action>.() -> List<Actor<Operation, Event>> = { listOf() }
) : Store<State, Event, Action>() {

    private val stateFlow = MutableStateFlow(initialState)
    private val eventsFlow = MutableSharedFlow<Event>(replay = 1)
    private val operationsFlow = MutableSharedFlow<Operation>(replay = 1)
    private val actionsFlow = MutableSharedFlow<Action>(replay = 1)

    init {
        start()
    }

    private fun start() {
        startActors()
        startEventsFlow()
        startOperationsFlow()
    }

    private fun startOperationsFlow() {
        operationsFlow.shareIn(storeScope + Dispatchers.Unconfined, SharingStarted.Eagerly)
    }

    private fun startEventsFlow() {
        eventsFlow
            .map { reducer.reduce(stateFlow.value, it) }
            .onEach { command -> command.state?.let { stateFlow.emit(it) } }
            .onEach { command -> command.operations.forEach { operationsFlow.emit(it) } }
            .onEach { command -> command.actions.forEach { actionsFlow.emit(it) } }
            .launchIn(storeScope + Dispatchers.Unconfined)
    }

    private fun startActors() {
        actors(this).forEach { actor ->
            storeScope.launch(Dispatchers.Unconfined) {
                actor.process(operationsFlow)
                    .onEach(eventsFlow::emit)
                    .launchIn(this)
            }
        }
    }

    override fun dispatch(event: Event) {
        storeScope.launch { eventsFlow.emit(event) }
    }

    override fun CoroutineScope.renderState(renderer: (State) -> Unit): Job {
        return stateFlow.onEach(renderer).launchIn(this)
    }

    override fun <UiState : Any> CoroutineScope.renderState(
        mapper: UiStateMapper<State, UiState>,
        renderer: (UiState) -> Unit
    ): Job {
        return stateFlow.map(mapper::map).onEach(renderer).launchIn(this)
    }

    override fun CoroutineScope.dispatchAction(dispatcher: (Action) -> Unit): Job {
        return actionsFlow.onEach(dispatcher).launchIn(this)
    }
}
