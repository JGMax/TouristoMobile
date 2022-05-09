package ru.inteam.touristo.recycler.list

internal interface ListOwner<T> {
    val size: Int

    fun positionOf(item: T): Int

    fun submitList(list: List<T>, onCommit: () -> Unit = {})
    operator fun get(position: Int): T
}
