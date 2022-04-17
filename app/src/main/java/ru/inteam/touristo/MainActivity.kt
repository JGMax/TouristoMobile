package ru.inteam.touristo

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import ru.inteam.touristo.common.data.remote.storage.CoroutineDataStorage
import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.common.tea.store.factory.TeaStoreFactory
import ru.inteam.touristo.common.ui.network.NetworkAppCompatActivity
import ru.inteam.touristo.common.util.value

data class Data(val a: Int)

suspend fun getData(params: Int): Data {
    delay(1000)
    Log.e("thread", Thread.currentThread().toString())
    return Data(params)
}

class State
class Reducer : Reducer<State, Nothing, Nothing, Nothing>() {
    override fun reduce(event: Nothing) {
        //TODO("Not yet implemented")
    }
}

class StoreFactory : TeaStoreFactory<State, Nothing, Nothing, Nothing>(
    State(),
    reducer = ru.inteam.touristo.Reducer()
)

class MainActivity : NetworkAppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStore = CoroutineDataStorage(
            source = { getData("KEY".value()) },
            viewModelStoreOwner = this
        )

        lifecycleScope.launchWhenResumed {
            dataStore.data("KEY" to 50)
                .onEach { Log.e("OUTPUT", it.toString()) }
                .collect()
        }
        dataStore.data("KEY" to 10)
    }

    override fun onConnectionChanged(isConnected: Boolean) {
        Log.e("connection", isConnected.toString())
    }
}
