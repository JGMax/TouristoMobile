package ru.inteam.touristo.recycler.clicks

import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.coroutines.flow.*
import ru.inteam.touristo.recycler.adapter.RecyclerAdapter
import ru.inteam.touristo.recycler.clicks.ClickEvent.HolderClick
import ru.inteam.touristo.recycler.clicks.ClickEvent.ItemClick
import ru.inteam.touristo.recycler.clicks.LongClickEvent.HolderLongClick
import ru.inteam.touristo.recycler.clicks.LongClickEvent.ItemLongClick
import ru.inteam.touristo.recycler.item.RecyclerItem

class ClicksManager internal constructor(
    private val adapter: RecyclerAdapter
) {
    private val clicksUnit: MutableSharedFlow<Flow<ClickEvent>> by lazy(
        LazyThreadSafetyMode.NONE
    ) { MutableSharedFlow(extraBufferCapacity = 10) }

    private val longClicksUnit: MutableSharedFlow<Flow<LongClickEvent>> by lazy(
        LazyThreadSafetyMode.NONE
    ) { MutableSharedFlow(extraBufferCapacity = 10) }

    val clicks = clicksUnit.flattenMerge().mapNotNull(::mapToItemClick)
    val longClicks = longClicksUnit.flattenMerge().mapNotNull(::mapToItemLongClick)

    private fun mapToItemClick(clickEvent: ClickEvent): ItemClick? {
        return when (clickEvent) {
            is HolderClick -> {
                val position = clickEvent.holder.absoluteAdapterPosition
                if (position == NO_POSITION) return null

                ItemClick(adapter[position], clickEvent.view)
            }
            is ItemClick -> clickEvent
        }
    }

    private fun mapToItemLongClick(clickEvent: LongClickEvent): ItemLongClick? {
        return when (clickEvent) {
            is HolderLongClick -> {
                val position = clickEvent.holder.absoluteAdapterPosition
                if (position == NO_POSITION) return null

                val item = adapter[position]
                ItemLongClick(item, clickEvent.view)
            }
            is ItemLongClick -> clickEvent
        }
    }

    inline fun <reified T : RecyclerItem<*, T>> clicks(): Flow<ItemClick> {
        return clicks.filter { it.item is T }
    }

    inline fun <reified T : RecyclerItem<*, T>> clicks(viewId: Int): Flow<T> {
        return clicks
            .filter { it.view.get()?.id == viewId }
            .mapNotNull { it.item as? T }
    }

    inline fun <reified T : RecyclerItem<*, T>> longClicks(): Flow<ItemLongClick> {
        return longClicks.filter { it.item is T }
    }

    inline fun <reified T : RecyclerItem<*, T>> longClicks(viewId: Int): Flow<T> {
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
