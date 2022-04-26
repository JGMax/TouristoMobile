package ru.inteam.touristo.common.ui.recycler.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem
import java.util.concurrent.atomic.AtomicBoolean

class RecyclerViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private var isFresh = AtomicBoolean(true)
    private var binding: ViewBinding? = null

    fun bind(item: RecyclerItem<*, *>) {
        if (isFresh.getAndSet(false)) {
            binding = item.provideViewBinding(itemView)
            binding?.let { item.initBy(it, this) }
        }
        binding?.let { item.bindTo(it) }
    }

    fun bind(item: RecyclerItem<*, *>, payloads: MutableList<Any>) {
        binding?.let { item.bindTo(it, payloads) }
    }
}
