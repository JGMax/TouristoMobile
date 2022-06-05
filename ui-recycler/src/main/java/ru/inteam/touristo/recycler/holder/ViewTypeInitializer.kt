package ru.inteam.touristo.recycler.holder

import android.view.View
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import ru.inteam.touristo.recycler.clicks.ClickEvent
import ru.inteam.touristo.recycler.clicks.ClickEvent.ItemClick
import ru.inteam.touristo.recycler.clicks.ClicksManager
import ru.inteam.touristo.recycler.clicks.LongClickEvent
import ru.inteam.touristo.recycler.clicks.LongClickEvent.ItemLongClick

@Suppress("UNCHECKED_CAST")
abstract class ViewTypeInitializer {
    private lateinit var clicksManager: ClicksManager

    protected abstract fun init(holder: RecyclerViewHolder)

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

    protected fun clicks(view: View, viewHolder: RecyclerViewHolder) {
        clicksManager.clicks(view, viewHolder)
    }

    protected fun longClicks(view: View, viewHolder: RecyclerViewHolder) {
        clicksManager.longClicks(view, viewHolder)
    }
}
