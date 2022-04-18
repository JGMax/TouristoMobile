package ru.inteam.touristo.common.ui.recycler.diff_util

import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem

class DefaultDiffCallback : DiffUtil.ItemCallback<RecyclerItem<*>>() {
    override fun areItemsTheSame(
        oldItem: RecyclerItem<*>,
        newItem: RecyclerItem<*>
    ): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(
        oldItem: RecyclerItem<*>,
        newItem: RecyclerItem<*>
    ): Boolean {
        return oldItem == newItem
    }
}
