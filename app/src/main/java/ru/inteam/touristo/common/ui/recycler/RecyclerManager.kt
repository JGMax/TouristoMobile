package ru.inteam.touristo.common.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.inteam.touristo.common.ui.recycler.adapter.RecyclerAdapter
import ru.inteam.touristo.common.ui.recycler.clicks.ClickEvent
import ru.inteam.touristo.common.ui.recycler.clicks.ClicksOwner
import ru.inteam.touristo.common.ui.recycler.clicks.LongClickEvent
import ru.inteam.touristo.common.ui.recycler.diff_util.DefaultDiffCallback
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem
import ru.inteam.touristo.common.ui.recycler.pagination.PageEvent
import ru.inteam.touristo.common.ui.recycler.pagination.PaginationOwner

class RecyclerManager(
    private val adapter: RecyclerAdapter
) : ClicksOwner by adapter {

    private val pageFlow: Flow<PageEvent>
        get() {
            if (adapter.pageFlow == null)
                error("Pagination is disabled")
            return adapter.pageFlow
        }

    fun submitList(items: List<RecyclerItem<*>>) {
        adapter.submitList(items)
    }

    inline fun <reified T : RecyclerItem<*>> clicks(): Flow<ClickEvent> {
        return clicksManager.clicks<T>()
    }

    inline fun <reified T : RecyclerItem<*>> clicks(viewId: Int): Flow<T> {
        return clicksManager.clicks(viewId)
    }

    inline fun <reified T : RecyclerItem<*>> longClicks(): Flow<LongClickEvent> {
        return clicksManager.longClicks<T>()
    }

    inline fun <reified T : RecyclerItem<*>> longClicks(viewId: Int): Flow<T> {
        return clicksManager.longClicks(viewId)
    }
}

class RecyclerManagerBuilder(private var recyclerView: RecyclerView?) {
    private var diffCallback: DiffUtil.ItemCallback<RecyclerItem<*>> = DefaultDiffCallback()
    private var paginationOwner: PaginationOwner? = null
    private var layoutManager: RecyclerView.LayoutManager =
        LinearLayoutManager(recyclerView?.context)
    private var adapter: RecyclerAdapter? = null
    private var decorations: Array<out RecyclerView.ItemDecoration> = emptyArray()

    fun diffCallback(diffCallback: DiffUtil.ItemCallback<RecyclerItem<*>>): RecyclerManagerBuilder {
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
