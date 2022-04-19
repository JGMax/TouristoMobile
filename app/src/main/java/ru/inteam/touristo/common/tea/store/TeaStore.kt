package ru.inteam.touristo.common.tea.store

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.inteam.touristo.common.tea.Actor
import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.UiStateMapper

class TeaStore<State : Any, Event : Any, Action : Any, Operation : Any>(
    initialState: State,
    private val reducer: Reducer<State, Event, Action, Operation>,
    private val actors: Store<*, *, *>.() -> List<Actor<Operation, Event>> = { listOf() }
) : Store<State, Event, Action>() {

    private val stateFlow = MutableStateFlow(initialState)
    private val eventsFlow = MutableSharedFlow<Event>()
    private val operationsFlow = MutableSharedFlow<Operation>()
    private val actionsFlow = MutableSharedFlow<Action>()

    init {
        start()
    }

    private fun start() {
        startMiddlewares()
        startEffectsFlow()
        startEventsFlow()
    }

    private fun startEffectsFlow() {
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

    private fun startMiddlewares() {
        actors(this).forEach { middleware ->
            storeScope.launch(Dispatchers.Unconfined) {
                middleware.process(operationsFlow)
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