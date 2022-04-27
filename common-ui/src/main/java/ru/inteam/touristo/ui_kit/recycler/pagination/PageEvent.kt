package ru.inteam.touristo.ui_kit.recycler.pagination

sealed class PageEvent {
    class NeedMore(val currentSize: Int, val pageSize: Int) : PageEvent()
}
