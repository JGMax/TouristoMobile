package ru.inteam.touristo.recycler.holder

import android.view.View
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import ru.inteam.touristo.recycler.clicks.ClickEvent
import ru.inteam.touristo.recycler.clicks.ClicksManager
import ru.inteam.touristo.recycler.clicks.LongClickEvent

@Suppress("UNCHECKED_CAST")
abstract class ViewType {
    private lateinit var clicksManager: ClicksManager

    abstract fun init(holder: RecyclerViewHolder)

    internal fun init(holder: RecyclerViewHolder, clicksManager: ClicksManager) {
        this.clicksManager = clicksManager
        init(holder)
    }

    protected fun <B : ViewBinding> RecyclerViewHolder.binding(
        binding: B,
        block: B.() -> Unit = {}
    ) {
        this.binding = binding
        binding.block()
    }

    @JvmName("clicksEvent")
    protected fun clicks(flow: Flow<ClickEvent.ItemClick>) {
        clicksManager.clicks(flow)
    }

    @JvmName("longClicksEvent")
    protected fun longClicks(flow: Flow<LongClickEvent.ItemLongClick>) {
        clicksManager.longClicks(flow)
    }

    protected fun clicks(flow: Flow<View>, viewHolder: RecyclerViewHolder) {
        clicksManager.clicks(flow, viewHolder)
    }

    protected fun longClicks(flow: Flow<View>, viewHolder: RecyclerViewHolder) {
        clicksManager.longClicks(flow, viewHolder)
    }
}
