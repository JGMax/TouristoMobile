package ru.inteam.touristo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.inteam.touristo.common.data.remote.storage.CoroutineDataStorage
import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.common.tea.store.factory.TeaStoreFactory
import ru.inteam.touristo.common.ui.network.NetworkAppCompatActivity
import ru.inteam.touristo.common.ui.recycler.adapter.RecyclerAdapter
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem
import ru.inteam.touristo.common.ui.recycler.manager
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.common.util.value
import ru.inteam.touristo.databinding.ItemTestBinding
import ru.inteam.touristo.databinding.ItemTestEBinding

data class Data(val a: Int)

suspend fun getData(params: Int): Data {
    delay(1000)
    Log.e("thread", Thread.currentThread().toString())
    return Data(params)
}

data class Uitem(
    val text: String
) : RecyclerItem<ItemTestBinding, Uitem>() {
    override val layoutId: Int = R.layout.item_test

    override fun provideViewBinding(view: View): ItemTestBinding {
        return ItemTestBinding.bind(view)
    }

    override fun ItemTestBinding.bind(me: Uitem) {
        text.text = this@Uitem.text
    }

    override fun ItemTestBinding.initHolder() {
        Log.e("init", "init")
        clicks(button.clicks())
    }
}

data class Titem(
    val text: String
) : RecyclerItem<ItemTestEBinding, Titem>() {
    override val layoutId: Int = R.layout.item_test_e

    override fun provideViewBinding(view: View): ItemTestEBinding {
        return ItemTestEBinding.bind(view)
    }

    override fun ItemTestEBinding.bind(me: Titem) {
        // Use var
        text.text = me.text
        // Use context
        text2.text = this@Titem.text + "2"
    }

    override fun ItemTestEBinding.initHolder() {
        Log.e("init", "init")
        clicks(button.clicks())
    }
}

class MainActivity : NetworkAppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        val recyclerManager = recycler.manager()

        recyclerManager.submitList(
            listOf(
                Uitem("aaa"),
                Uitem("bbb"),
                Titem("ttt")
            )
        )

        val dataStore = CoroutineDataStorage(
            source = { getData("KEY".value()) },
            viewModelStoreOwner = this
        )

        lifecycleScope.launchWhenResumed {
            launch {
                recyclerManager.clicks<Titem>(R.id.button)
                    .onEach { Log.e("click", it.toString()) }
                    .collect()
            }
            launch {
                dataStore.data("KEY" to 10)
                    .onEach { Log.e("data", it.toString()) }
                    .collect()
            }
        }
    }
}
