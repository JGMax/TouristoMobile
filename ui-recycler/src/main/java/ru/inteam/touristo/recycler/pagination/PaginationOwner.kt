package ru.inteam.touristo.recycler.pagination

import kotlinx.coroutines.flow.MutableSharedFlow

interface PaginationOwner {
    companion object {
        const val DEFAULT_PAGE_SIZE = 20L
        const val DEFAULT_PAGE_OFFSET = 10L
    }

    val pageSize: Long
    val pageOffset: Long
    val pageEventFlow: MutableSharedFlow<PageEvent>
}
