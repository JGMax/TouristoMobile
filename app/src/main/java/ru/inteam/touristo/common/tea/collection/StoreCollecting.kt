package ru.inteam.touristo.common.tea.collection

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.UiStateMapper

fun <State : Any, Action : Any> Store<State, *, Action>.collect(
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    stateRender: ((State) -> Unit)? = null,
    actionDispatcher: ((Action) -> Unit)? = null
): Unit = with(lifecycleCoroutineScope) {
    if (stateRender != null) launchWhenStarted { renderState(stateRender) }
    if (actionDispatcher != null) launchWhenResumed { dispatchAction(actionDispatcher) }
}

fun <State : Any, UiState : Any, Action : Any> Store<State, *, Action>.collect(
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    uiStateMapper: UiStateMapper<State, UiState>,
    stateRender: (UiState) -> Unit,
    actionDispatcher: ((Action) -> Unit)? = null
): Unit = with(lifecycleCoroutineScope) {
    launchWhenStarted { renderState(uiStateMapper, stateRender) }
    if (actionDispatcher != null) launchWhenResumed { dispatchAction(actionDispatcher) }
}

fun <State : Any, OutState : Any, Action : Any> Store<State, *, Action>.collect(
    coroutineScope: CoroutineScope,
    stateMapper: (State) -> OutState,
    stateRender: (OutState) -> Unit
): Job = coroutineScope.launch { renderState(stateMapper, stateRender) }
