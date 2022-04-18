package ru.inteam.touristo.common.ui.recycler.pagination

sealed class PageEvent {
    class NeedMore(val currentSize: Int, val pageSize: Int) : PageEvent()
}
