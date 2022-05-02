package ru.inteam.touristo.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asSharedFlow
import ru.inteam.touristo.recycler.clicks.ClicksManager
import ru.inteam.touristo.recycler.clicks.ClicksOwner
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.item.RecyclerItem
import ru.inteam.touristo.recycler.pagination.PageEvent
import ru.inteam.touristo.recycler.pagination.PageEvent.NeedMore
import ru.inteam.touristo.recycler.pagination.PaginationOwner
import ru.inteam.touristo.common.ui.view.inflate

class RecyclerAdapter(
    private val paginationOwner: PaginationOwner?,
    diffCallback: DiffUtil.ItemCallback<RecyclerItem<*, *>>
) : RecyclerView.Adapter<RecyclerViewHolder>(), ClicksOwner {
    override val clicksManager = ClicksManager(this)

    val pageFlow: Flow<PageEvent>? = paginationOwner?.pageEventFlow?.asSharedFlow()

    private val differ: AsyncListDiffer<RecyclerItem<*, *>> by lazy {
        AsyncListDiffer(this, diffCallback)
    }
    private var asyncClicksAttachmentJob: Job? = null

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        if (paginationOwner != null) {
            val position = holder.absoluteAdapterPosition
            if (position == NO_POSITION) return
            if (position == itemCount - paginationOwner.pageOffset) {
                paginationOwner.run { pageEventFlow.tryEmit(NeedMore(itemCount, pageSize)) }
            }
        } else {
            super.onViewAttachedToWindow(holder)
        }
    }

    fun submitList(list: List<RecyclerItem<*, *>>, onCommit: () -> Unit = {}) {
        differ.submitList(list, onCommit)
        asyncClicksAttachmentJob?.cancel()
        asyncClicksAttachmentJob = CoroutineScope(Dispatchers.Default).launch {
            for (item in list) {
                clicksManager.attachClicks(item)
                clicksManager.attachLongClicks(item)
                if (!isActive) break
            }
        }
    }

    override fun getItemViewType(position: Int): Int = differ[position].layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(parent.inflate(viewType))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(differ[position])
    }

    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bind(differ[position], payloads)
        }
    }

    override fun getItemCount(): Int = differ.size

    private val AsyncListDiffer<*>.size
        get() = currentList.size

    private operator fun <T> AsyncListDiffer<T>.get(position: Int): T = currentList[position]

    operator fun get(position: Int) = differ[position]
}
