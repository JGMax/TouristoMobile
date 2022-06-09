package ru.inteam.touristo.common.tea

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.plus
import ru.inteam.touristo.common.tea.store.factory.TeaStoreOwner

abstract class Store<out State : Any, in Event : Any, out Action : Any> : ViewModel(),
    TeaStoreOwner {
    val storeScope: CoroutineScope
        get() = viewModelScope + Dispatchers.Unconfined

    @CallSuper
    override fun onCleared() {
        viewModelStore.clear()
    }

    internal abstract fun CoroutineScope.renderState(renderer: (State) -> Unit): Job

    internal abstract fun <UiState : Any> CoroutineScope.renderState(
        mapper: UiStateMapper<State, UiState>,
        renderer: (UiState) -> Unit
    ): Job

    internal abstract fun CoroutineScope.dispatchAction(dispatcher: (Action) -> Unit): Job

    abstract fun dispatch(event: Event)
}
