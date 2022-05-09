package ru.inteam.touristo.recycler.list

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

internal class SimpleListOwner<T>(
    private val adapter: RecyclerView.Adapter<*>
) : ListOwner<T> {

    private var list: List<T> = emptyList()

    override val size: Int
        get() = list.size

    @SuppressLint("NotifyDataSetChanged")
    override fun submitList(list: List<T>, onCommit: () -> Unit) {
        this.list = list
        adapter.notifyDataSetChanged()
        onCommit()
    }

    override fun get(position: Int): T = list[position]

    override fun positionOf(item: T): Int = list.indexOf(item)
}
