package ru.inteam.touristo.common.ui.recycler.item

import android.view.View
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import ru.inteam.touristo.common.ui.recycler.clicks.ClickEvent
import ru.inteam.touristo.common.ui.recycler.clicks.LongClickEvent
import java.lang.ref.WeakReference

abstract class RecyclerItem<B : ViewBinding, ME> {
    abstract val layoutId: Int
    open val itemId: String = this::class.java.canonicalName ?: ""

    private var clicks: MutableSharedFlow<Flow<ClickEvent>>? = null
    private var longClicks: MutableSharedFlow<Flow<LongClickEvent>>? = null

    abstract fun provideViewBinding(view: View): B

    protected open fun B.initHolder() = Unit
    protected open fun B.bind(me: ME) = Unit
    protected open fun B.bind(me: ME, payloads: MutableList<Any>) = Unit

    fun initBy(binding: ViewBinding) = (binding as B).initHolder()
    fun bindTo(binding: ViewBinding) = (binding as B).bind(this as ME)
    fun bindTo(binding: ViewBinding, payloads: MutableList<Any>) {
        return (binding as B).bind(this as ME, payloads)
    }

    @JvmName("clicksEvent")
    protected fun clicks(flow: Flow<ClickEvent>) {
        clicks?.tryEmit(flow)
    }

    @JvmName("longClicksEvent")
    protected fun clicks(flow: Flow<LongClickEvent>) {
        longClicks?.tryEmit(flow)
    }

    protected fun clicks(flow: Flow<View>) {
        clicks?.tryEmit(flow.map { ClickEvent(this, WeakReference(it)) })
    }

    protected fun longClicks(flow: Flow<View>) {
        longClicks?.tryEmit(flow.map { LongClickEvent(this, WeakReference(it)) })
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
