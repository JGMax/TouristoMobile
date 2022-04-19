package ru.inteam.touristo.common.ui.recycler.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem

class RecyclerViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private var isFresh = true
    private lateinit var binding: ViewBinding

    fun bind(item: RecyclerItem<*, *>) {
        if (isFresh) {
            binding = item.provideViewBinding(itemView)
            item.initBy(binding)
            isFresh = false
        }
        item.bindTo(binding)
    }

    fun bind(item: RecyclerItem<*, *>, payloads: MutableList<Any>) {
        item.bindTo(binding, payloads)
    }
}
