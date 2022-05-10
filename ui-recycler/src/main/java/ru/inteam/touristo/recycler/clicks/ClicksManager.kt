package ru.inteam.touristo.recycler.clicks

import android.view.View
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.coroutines.flow.*
import ru.inteam.touristo.recycler.adapter.RecyclerAdapter
import ru.inteam.touristo.recycler.clicks.ClickEvent.HolderClick
import ru.inteam.touristo.recycler.clicks.ClickEvent.ItemClick
import ru.inteam.touristo.recycler.clicks.LongClickEvent.HolderLongClick
import ru.inteam.touristo.recycler.clicks.LongClickEvent.ItemLongClick
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.item.RecyclerItem
import java.lang.ref.WeakReference

class ClicksManager internal constructor(
    private val adapter: RecyclerAdapter
) {
    private val clicksUnit: MutableSharedFlow<Flow<ClickEvent>> by lazy(
        LazyThreadSafetyMode.NONE
    ) { MutableSharedFlow(extraBufferCapacity = 100) }

    private val longClicksUnit: MutableSharedFlow<Flow<LongClickEvent>> by lazy(
        LazyThreadSafetyMode.NONE
    ) { MutableSharedFlow(extraBufferCapacity = 100) }

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

    inline fun <reified T : RecyclerItem> clicks(): Flow<ItemClick> {
        return clicks.filter { it.item is T }
    }

    inline fun <reified T : RecyclerItem> clicks(viewId: Int): Flow<T> {
        return clicks
            .filter { it.view.get()?.id == viewId }
            .mapNotNull { it.item as? T }
    }

    inline fun <reified T : RecyclerItem> longClicks(): Flow<ItemLongClick> {
        return longClicks.filter { it.item is T }
    }

    inline fun <reified T : RecyclerItem> longClicks(viewId: Int): Flow<T> {
        return longClicks
            .filter { it.view.get()?.id == viewId }
            .mapNotNull { it.item as? T }
    }

    @JvmName("clicksEvent")
    internal fun clicks(flow: Flow<ItemClick>) {
        clicksUnit.tryEmit(flow)
    }

    @JvmName("longClicksEvent")
    internal fun longClicks(flow: Flow<ItemLongClick>) {
        longClicksUnit.tryEmit(flow)
    }

    internal fun clicks(flow: Flow<View>, viewHolder: RecyclerViewHolder) {
        clicksUnit.tryEmit(flow.map { HolderClick(viewHolder, WeakReference(it)) })
    }

    internal fun longClicks(flow: Flow<View>, viewHolder: RecyclerViewHolder) {
        longClicksUnit.tryEmit(flow.map {
            HolderLongClick(
                viewHolder,
                WeakReference(it)
            )
        })
    }
}
