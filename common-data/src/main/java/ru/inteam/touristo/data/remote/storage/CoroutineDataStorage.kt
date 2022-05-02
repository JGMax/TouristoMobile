package ru.inteam.touristo.data.remote.storage

import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.inteam.touristo.data.DataStorage
import ru.inteam.touristo.data.remote.presentation.CoroutineDataEvent
import ru.inteam.touristo.data.remote.presentation.CoroutineDataStore
import ru.inteam.touristo.data.remote.presentation.CoroutineDataStoreFactory
import ru.inteam.touristo.data.remote.storage.source.SourceManager
import ru.inteam.touristo.data.state.LoadingState
import ru.inteam.touristo.common.tea.collection.collect
import ru.inteam.touristo.common.tea.store.factory.TeaStore
import ru.inteam.touristo.common.tea.store.factory.TeaStoreOwner

private const val COROUTINE_DATA_STORE_KEY = "CoroutineDataStorage"

class CoroutineDataStorage<T>(
    source: suspend Map<String, Any>.() -> T,
    vararg staticParams: Pair<String, Any>,
    viewModelStoreOwner: ViewModelStoreOwner? = null
) : TeaStoreOwner, DataStorage<T> {
    private val storeOwner = viewModelStoreOwner ?: this

    private val sourceManager = SourceManager(source).apply { setStaticParams(*staticParams) }

    private val store: CoroutineDataStore<T> by TeaStore(storeOwner, COROUTINE_DATA_STORE_KEY) {
        CoroutineDataStoreFactory(sourceManager::invoke)
    }

    private val stateFlow = MutableSharedFlow<LoadingState<T>>(extraBufferCapacity = 10)
    private val state = stateFlow.asSharedFlow()

    init {
        store.collect(store.storeScope, { it.loadingState }, stateFlow::tryEmit)
    }

    override fun data(vararg params: Pair<String, Any>): Flow<LoadingState<T>> {
        update(*params)
        return state
    }

    private fun update(vararg params: Pair<String, Any>) {
        if (params.isNotEmpty()) {
            sourceManager.setParams(*params)
        }
        store.dispatch(CoroutineDataEvent.Update)
    }
}
