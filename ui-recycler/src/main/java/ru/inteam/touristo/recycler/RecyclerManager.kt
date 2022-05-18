package ru.inteam.touristo.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.inteam.touristo.recycler.adapter.RecyclerAdapter
import ru.inteam.touristo.recycler.clicks.ClicksOwner
import ru.inteam.touristo.recycler.holder.ViewTypeFactory
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

    fun submitList(items: List<RecyclerItem>, onCommit: () -> Unit = {}) {
        adapter.submitList(items, onCommit)
    }
}

class RecyclerManagerBuilder internal constructor(private var recyclerView: RecyclerView?) {
    private var diffCallback: DiffUtil.ItemCallback<RecyclerItem>? = null
    private var paginationOwner: PaginationOwner? = null
    private var layoutManager: RecyclerView.LayoutManager =
        LinearLayoutManager(recyclerView?.context)
    private var adapter: RecyclerAdapter? = null
    private var decorations: Array<out RecyclerView.ItemDecoration> = emptyArray()
    private var viewTypeFactory: ViewTypeFactory? = null

    fun viewTypeFactory(viewTypeFactory: ViewTypeFactory): RecyclerManagerBuilder {
        this.viewTypeFactory = viewTypeFactory
        return this
    }

    fun diffCallback(
        diffCallback: DiffUtil.ItemCallback<RecyclerItem>
    ): RecyclerManagerBuilder {
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
        val adapter = adapter ?: RecyclerAdapter(paginationOwner, diffCallback, viewTypeFactory)
        recyclerView?.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
            decorations.forEach(it::addItemDecoration)
        }
        val manager = RecyclerManager(adapter)
        recyclerView = null
        return manager
    }
}

fun RecyclerView.managerBuilder(): RecyclerManagerBuilder {
    return RecyclerManagerBuilder(this)
}

fun RecyclerView.manager(): RecyclerManager = managerBuilder().build()

fun RecyclerView.manager(builder: RecyclerManagerBuilder.() -> Unit): RecyclerManager {
    return managerBuilder().apply(builder).build()
}
