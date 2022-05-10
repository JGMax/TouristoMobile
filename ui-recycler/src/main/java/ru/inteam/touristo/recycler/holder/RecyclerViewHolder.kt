package ru.inteam.touristo.recycler.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.inteam.touristo.recycler.clicks.ClicksManager
import ru.inteam.touristo.recycler.item.RecyclerItem

class RecyclerViewHolder internal constructor(
    view: View,
    viewType: ViewType?,
    clicksManager: ClicksManager
) : RecyclerView.ViewHolder(view) {

    internal lateinit var binding: ViewBinding

    init {
        viewType?.init(this, clicksManager)
    }

    fun bind(item: RecyclerItem) {
        item.bind(this)
    }

    fun bind(item: RecyclerItem, payloads: MutableList<Any>) {
        item.bind(this, payloads)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : ViewBinding> RecyclerViewHolder.binding(init: T.() -> Unit) = (binding as T).init()
