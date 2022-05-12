package ru.inteam.touristo.recycler.clicks

import android.view.View
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import ru.inteam.touristo.common.util.weak
import ru.inteam.touristo.recycler.adapter.RecyclerAdapter
import ru.inteam.touristo.recycler.clicks.ClickEvent.HolderClick
import ru.inteam.touristo.recycler.clicks.ClickEvent.ItemClick
import ru.inteam.touristo.recycler.clicks.LongClickEvent.HolderLongClick
import ru.inteam.touristo.recycler.clicks.LongClickEvent.ItemLongClick
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.item.RecyclerItem

class ClicksManager internal constructor(
    private val adapter: RecyclerAdapter
) {

    private val _clicks = MutableSharedFlow<ClickEvent>(extraBufferCapacity = 100)
    private val _longClicks = MutableSharedFlow<LongClickEvent>(extraBufferCapacity = 100)

    val clicks = _clicks.mapNotNull(::mapToItemClick)
    val longClicks = _longClicks.mapNotNull(::mapToItemLongClick)

    private fun mapToItemClick(clickEvent: ClickEvent): ItemClick? {
        return when (clickEvent) {
            is HolderClick -> {
                val position = clickEvent.holder.get()?.absoluteAdapterPosition ?: NO_POSITION
                if (position == NO_POSITION) return null

                ItemClick(adapter[position], clickEvent.view)
            }
            is ItemClick -> clickEvent
        }
    }

    private fun mapToItemLongClick(clickEvent: LongClickEvent): ItemLongClick? {
        return when (clickEvent) {
            is HolderLongClick -> {
                val position = clickEvent.holder.get()?.absoluteAdapterPosition ?: NO_POSITION
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

    internal fun clicks(view: View, viewHolder: RecyclerViewHolder) {
        val viewHolderWeak = viewHolder.weak()

        view.setOnClickListener { v ->
            if (viewHolderWeak.get() == null) {
                v.setOnClickListener(null)
            } else {
                _clicks.tryEmit(HolderClick(viewHolderWeak, v.weak()))
            }
        }
    }

    internal fun longClicks(view: View, viewHolder: RecyclerViewHolder) {
        val viewHolderWeak = viewHolder.weak()

        view.setOnLongClickListener { v ->
            if (viewHolderWeak.get() == null) {
                v.setOnLongClickListener(null)
                false
            } else {
                _longClicks.tryEmit(HolderLongClick(viewHolderWeak, v.weak()))
                true
            }
        }
    }
}
