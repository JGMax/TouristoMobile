package ru.inteam.touristo.recycler.diff_util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.inteam.touristo.recycler.item.RecyclerItem

internal class DefaultDiffCallback : DiffUtil.ItemCallback<RecyclerItem<*, *>>() {
    override fun areItemsTheSame(
        oldItem: RecyclerItem<*, *>,
        newItem: RecyclerItem<*, *>
    ): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: RecyclerItem<*, *>,
        newItem: RecyclerItem<*, *>
    ): Boolean {
        return oldItem == newItem
    }
}
