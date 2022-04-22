package ru.inteam.touristo.common.ui.recycler.item

import android.view.View
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import ru.inteam.touristo.common.ui.recycler.clicks.ClickEvent
import ru.inteam.touristo.common.ui.recycler.clicks.ClickEvent.HolderClick
import ru.inteam.touristo.common.ui.recycler.clicks.ClickEvent.ItemClick
import ru.inteam.touristo.common.ui.recycler.clicks.LongClickEvent
import ru.inteam.touristo.common.ui.recycler.clicks.LongClickEvent.HolderLongClick
import ru.inteam.touristo.common.ui.recycler.clicks.LongClickEvent.ItemLongClick
import ru.inteam.touristo.common.ui.recycler.holder.RecyclerViewHolder
import java.lang.ref.WeakReference

@Suppress("UNCHECKED_CAST")
abstract class RecyclerItem<B : ViewBinding, ME> {
    abstract val layoutId: Int
    open val itemId: String = this::class.java.canonicalName ?: ""

    private var clicks: MutableSharedFlow<Flow<ClickEvent>>? = null
    private var longClicks: MutableSharedFlow<Flow<LongClickEvent>>? = null

    abstract fun provideViewBinding(view: View): B

    protected open fun RecyclerViewHolder.initHolder(binding: B) = Unit
    protected open fun B.bind(me: ME) = Unit
    protected open fun B.bind(me: ME, payloads: MutableList<Any>) = Unit

    fun initBy(binding: ViewBinding, holder: RecyclerViewHolder) = holder.initHolder(binding as B)
    fun bindTo(binding: ViewBinding) = (binding as B).bind(this as ME)
    fun bindTo(binding: ViewBinding, payloads: MutableList<Any>) {
        return (binding as B).bind(this as ME, payloads)
    }

    @JvmName("clicksEvent")
    protected fun clicks(flow: Flow<ItemClick>) {
        clicks?.tryEmit(flow)
    }

    @JvmName("longClicksEvent")
    protected fun longClicks(flow: Flow<ItemLongClick>) {
        longClicks?.tryEmit(flow)
    }

    protected fun clicks(flow: Flow<View>, viewHolder: RecyclerViewHolder) {
        clicks?.tryEmit(flow.map { HolderClick(viewHolder, WeakReference(it)) })
    }

    protected fun longClicks(flow: Flow<View>, viewHolder: RecyclerViewHolder) {
        longClicks?.tryEmit(flow.map { HolderLongClick(viewHolder, WeakReference(it)) })
    }

    fun attachClicks(flow: MutableSharedFlow<Flow<ClickEvent>>) {
        clicks = flow
    }

    fun attachLongClicks(flow: MutableSharedFlow<Flow<LongClickEvent>>) {
        longClicks = flow
    }

    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
}
