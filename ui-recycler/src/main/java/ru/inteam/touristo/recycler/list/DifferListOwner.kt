package ru.inteam.touristo.recycler.list

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

internal class DifferListOwner<T>(
    adapter: RecyclerView.Adapter<*>,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListOwner<T> {

    private val differ = AsyncListDiffer(adapter, diffCallback)

    override val size: Int
        get() = differ.currentList.size

    override fun submitList(list: List<T>, onCommit: () -> Unit) {
        differ.submitList(list, onCommit)
    }

    override fun get(position: Int): T = differ.currentList[position]

    override fun positionOf(item: T): Int = differ.currentList.indexOf(item)
}
