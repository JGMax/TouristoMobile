@file:Suppress("UNCHECKED_CAST")

package ru.inteam.touristo.common.tea.store.factory

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import ru.inteam.touristo.common.tea.Actor
import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.store.TeaStore

open class TeaStoreFactory<State : Any, Event : Any, Action : Any, Operation : Any>(
    private val initialState: State,
    private val reducer: Reducer<State, Event, Action, Operation>,
    private val actors: Store<State, Event, Action>.() -> List<Actor<Operation, Event>> = { listOf() }
) : ViewModelProvider.Factory {

    constructor(
        initialState: State,
        reducer: Reducer<State, Event, Action, Operation>,
        actors: List<Actor<Operation, Event>>
    ) : this(initialState, reducer, { actors })

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        teaStore(initialState, reducer, actors) as T
}

private fun <State : Any, Event : Any, Action : Any, Operation : Any> teaStore(
    initialState: State,
    reducer: Reducer<State, Event, Action, Operation>,
    actors: Store<State, Event, Action>.() -> List<Actor<Operation, Event>> = { listOf() }
): Store<State, Event, Action> = TeaStore(initialState, reducer, actors)

inline fun <reified S : Store<*, *, *>> ComponentActivity.TeaStore(
    key: String? = null,
    noinline factory: () -> TeaStoreFactory<*, *, *, *>
): Lazy<S> = StoreLazy(S::class, key, { viewModelStore }, factory)

inline fun <reified S : Store<*, *, *>> Fragment.TeaStore(
    viewModelStoreOwner: ViewModelStoreOwner = this,
    key: String? = null,
    noinline factory: () -> TeaStoreFactory<*, *, *, *>
): Lazy<S> = StoreLazy(S::class, key, { viewModelStoreOwner.viewModelStore }, factory)

inline fun <reified S : Store<*, *, *>> Fragment.ActivityTeaStore(
    key: String? = null,
    noinline factory: () -> TeaStoreFactory<*, *, *, *>
): Lazy<S> = StoreLazy(S::class, key, { requireActivity().viewModelStore }, factory)

inline fun <reified S : Store<*, *, *>> TeaStoreOwner.TeaStore(
    viewModelStoreOwner: ViewModelStoreOwner = this,
    key: String? = null,
    noinline factory: () -> TeaStoreFactory<*, *, *, *>
): Lazy<S> = StoreLazy(S::class, key, { viewModelStoreOwner.viewModelStore }, factory)
