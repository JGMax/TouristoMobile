package ru.inteam.touristo.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asSharedFlow
import ru.inteam.touristo.common.ui.view.inflate
import ru.inteam.touristo.recycler.clicks.ClicksManager
import ru.inteam.touristo.recycler.clicks.ClicksOwner
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.holder.ViewTypeInitializer
import ru.inteam.touristo.recycler.item.RecyclerItem
import ru.inteam.touristo.recycler.list.DifferListOwner
import ru.inteam.touristo.recycler.list.ListOwner
import ru.inteam.touristo.recycler.list.SimpleListOwner
import ru.inteam.touristo.recycler.pagination.PageEvent
import ru.inteam.touristo.recycler.pagination.PaginationOwner

class RecyclerAdapter(
    private val paginationOwner: PaginationOwner?,
    diffCallback: DiffUtil.ItemCallback<RecyclerItem>?
) : RecyclerView.Adapter<RecyclerViewHolder>(), ClicksOwner {

    override val clicksManager = ClicksManager(this)
    private val viewTypes: MutableMap<Int, () -> ViewTypeInitializer> = mutableMapOf()

    val pageFlow: Flow<PageEvent>? = paginationOwner?.pageEventFlow?.asSharedFlow()

    private val listOwner: ListOwner<RecyclerItem> by lazy {
        diffCallback?.let { DifferListOwner(this, it) } ?: SimpleListOwner(this)
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        if (paginationOwner != null) {
            val position = holder.absoluteAdapterPosition

            if (position == NO_POSITION) return

            if (position == (itemCount - paginationOwner.pageOffset).toInt()) {
                paginationOwner.run {
                    pageEventFlow.tryEmit(PageEvent(itemCount.toLong(), pageSize))
                }
            }
        } else {
            super.onViewAttachedToWindow(holder)
        }
    }

    fun submitList(list: List<RecyclerItem>, onCommit: () -> Unit = {}) {
        listOwner.submitList(list, onCommit)
    }

    override fun getItemViewType(position: Int): Int {
        val item = listOwner[position]
        if (item.viewTypeFactory != null && item.layoutId !in viewTypes) {
            viewTypes[item.layoutId] = item.viewTypeFactory
        }
        return item.layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            parent.inflate(viewType),
            viewTypes[viewType]?.invoke(),
            clicksManager
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(listOwner[position])
    }

    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bind(listOwner[position], payloads)
        }
    }

    override fun getItemCount(): Int = listOwner.size

    operator fun get(position: Int) = listOwner[position]

    fun positionOf(item: RecyclerItem): Int = listOwner.positionOf(item)
}
