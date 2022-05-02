package ru.inteam.touristo.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.inteam.touristo.recycler.adapter.RecyclerAdapter
import ru.inteam.touristo.recycler.clicks.ClickEvent.ItemClick
import ru.inteam.touristo.recycler.clicks.ClicksManager
import ru.inteam.touristo.recycler.clicks.ClicksOwner
import ru.inteam.touristo.recycler.clicks.LongClickEvent.ItemLongClick
import ru.inteam.touristo.recycler.diff_util.DefaultDiffCallback
import ru.inteam.touristo.recycler.item.RecyclerItem
import ru.inteam.touristo.recycler.pagination.PageEvent
import ru.inteam.touristo.recycler.pagination.PaginationOwner

class RecyclerManager internal constructor(
    private val adapter: RecyclerAdapter
) : ClicksOwner by adapter {

    val pageFlow: Flow<PageEvent>
        get() {
            if (adapter.pageFlow == null)
                error("Pagination is disabled")
            return adapter.pageFlow
        }

    val itemCount: Int
        get() = adapter.itemCount

    fun submitList(items: List<RecyclerItem<*, *>>, onCommit: () -> Unit = {}) {
        adapter.submitList(items, onCommit)
    }

    inline fun <reified T : RecyclerItem<*, *>> clicks(): Flow<ItemClick> {
        return clicksManager.clicks<T>()
    }

    inline fun <reified T : RecyclerItem<*, *>> clicks(viewId: Int): Flow<T> {
        return clicksManager.clicks(viewId)
    }

    inline fun <reified T : RecyclerItem<*, *>> longClicks(): Flow<ItemLongClick> {
        return clicksManager.longClicks<T>()
    }

    inline fun <reified T : RecyclerItem<*, *>> longClicks(viewId: Int): Flow<T> {
        return clicksManager.longClicks(viewId)
    }
}

class RecyclerManagerBuilder internal constructor(private var recyclerView: RecyclerView?) {
    private var diffCallback: DiffUtil.ItemCallback<RecyclerItem<*, *>> = DefaultDiffCallback()
    private var paginationOwner: PaginationOwner? = null
    private var layoutManager: RecyclerView.LayoutManager =
        LinearLayoutManager(recyclerView?.context)
    private var adapter: RecyclerAdapter? = null
    private var decorations: Array<out RecyclerView.ItemDecoration> = emptyArray()

    fun diffCallback(diffCallback: DiffUtil.ItemCallback<RecyclerItem<*, *>>): RecyclerManagerBuilder {
        this.diffCallback = diffCallback
        return this
    }

    fun enablePagination(
        pageSize: Int = PaginationOwner.DEFAULT_PAGE_SIZE,
        pageOffset: Int = PaginationOwner.DEFAULT_PAGE_OFFSET
    ): RecyclerManagerBuilder {
        paginationOwner = object : PaginationOwner {
            override val pageSize: Int = pageSize
            override val pageOffset: Int = pageOffset
            override val pageEventFlow = MutableSharedFlow<PageEvent>(extraBufferCapacity = 10)
        }
        return this
    }

    fun layoutManager(layoutManager: RecyclerView.LayoutManager): RecyclerManagerBuilder {
        this.layoutManager = layoutManager
        return this
    }

    fun adapter(adapter: RecyclerAdapter): RecyclerManagerBuilder {
        this.adapter = adapter
        return this
    }

    fun decorations(vararg decorations: RecyclerView.ItemDecoration): RecyclerManagerBuilder {
        this.decorations = decorations
        return this
    }

    fun build(): RecyclerManager {
        val adapter = adapter ?: RecyclerAdapter(paginationOwner, diffCallback)
        recyclerView?.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
            decorations.forEach(it::addItemDecoration)
        }
        recyclerView = null
        return RecyclerManager(adapter)
    }
}

fun RecyclerView.managerBuilder(): RecyclerManagerBuilder = RecyclerManagerBuilder(this)

fun RecyclerView.manager(): RecyclerManager = managerBuilder().build()
