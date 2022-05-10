package ru.inteam.touristo.recycler.item

import ru.inteam.touristo.recycler.holder.RecyclerViewHolder

@Suppress("UNCHECKED_CAST")
abstract class RecyclerItem {
    abstract val layoutId: Int
    open val itemId: String = this::class.java.canonicalName ?: ""

    open fun bind(holder: RecyclerViewHolder) = Unit
    open fun bind(holder: RecyclerViewHolder, payloads: MutableList<Any>) = Unit
}
