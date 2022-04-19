package ru.inteam.touristo.common.ui.recycler.clicks

import kotlinx.coroutines.flow.*
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem

class ClicksManager {
    private val clicksUnit: MutableSharedFlow<Flow<ClickEvent>> by lazy(LazyThreadSafetyMode.NONE) {
        MutableSharedFlow(extraBufferCapacity = 10)
    }

    private val longClicksUnit: MutableSharedFlow<Flow<LongClickEvent>> by lazy(LazyThreadSafetyMode.NONE) {
        MutableSharedFlow(extraBufferCapacity = 10)
    }

    val clicks = clicksUnit.flattenMerge()
    val longClicks = longClicksUnit.flattenMerge()

    inline fun <reified T : RecyclerItem<*, *>> clicks(): Flow<ClickEvent> {
        return clicks.filter { it.item is T }
    }

    inline fun <reified T : RecyclerItem<*, *>> clicks(viewId: Int): Flow<T> {
        return clicks
            .filter { it.view.get()?.id == viewId }
            .mapNotNull { it.item as? T }
    }

    inline fun <reified T : RecyclerItem<*, *>> longClicks(): Flow<LongClickEvent> {
        return longClicks.filter { it.item is T }
    }

    inline fun <reified T : RecyclerItem<*, *>> longClicks(viewId: Int): Flow<T> {
        return longClicks
            .filter { it.view.get()?.id == viewId }
            .mapNotNull { it.item as? T }
    }

    fun attachClicks(item: RecyclerItem<*, *>) {
        item.attachClicks(clicksUnit)
    }

    fun attachLongClicks(item: RecyclerItem<*, *>) {
        item.attachLongClicks(longClicksUnit)
    }
}
