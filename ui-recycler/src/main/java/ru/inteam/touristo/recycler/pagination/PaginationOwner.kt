package ru.inteam.touristo.recycler.pagination

import kotlinx.coroutines.flow.MutableSharedFlow

interface PaginationOwner {
    companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val DEFAULT_PAGE_OFFSET = 10
    }

    val pageSize: Int
    val pageOffset: Int
    val pageEventFlow: MutableSharedFlow<PageEvent>
}
