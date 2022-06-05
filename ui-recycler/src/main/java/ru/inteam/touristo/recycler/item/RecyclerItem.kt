package ru.inteam.touristo.recycler.item

import androidx.viewbinding.ViewBinding
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.holder.ViewTypeInitializer

abstract class RecyclerItem(val viewTypeFactory: (() -> ViewTypeInitializer)? = null) {
    abstract val layoutId: Int
    open val itemId: String = this::class.java.canonicalName ?: ""

    open fun bind(holder: RecyclerViewHolder) = Unit
    open fun bind(holder: RecyclerViewHolder, payloads: MutableList<Any>) = Unit
}
