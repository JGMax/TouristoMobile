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
    private lateinit var binding: ViewBinding

    fun bind(item: RecyclerItem<*, *>) {
        if (isFresh.getAndSet(false)) {
            binding = item.provideViewBinding(itemView)
            item.initBy(binding, this)
        }
        item.bindTo(binding)
    }

    fun bind(item: RecyclerItem<*, *>, payloads: MutableList<Any>) {
        item.bindTo(binding, payloads)
    }
}
