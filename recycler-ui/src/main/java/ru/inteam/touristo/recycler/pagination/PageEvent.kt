package ru.inteam.touristo.recycler.pagination

sealed class PageEvent {
    class NeedMore(val currentSize: Int, val pageSize: Int) : PageEvent()
}
