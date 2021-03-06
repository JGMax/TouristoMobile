package ru.inteam.touristo.recycler.clicks

import android.view.View
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.item.RecyclerItem
import java.lang.ref.WeakReference

sealed class ClickEvent(val view: WeakReference<View>) {
    class HolderClick(
        val holder: WeakReference<RecyclerViewHolder>,
        view: WeakReference<View>
    ) : ClickEvent(view)

    class ItemClick(val item: RecyclerItem, view: WeakReference<View>) : ClickEvent(view)
}

sealed class LongClickEvent(val view: WeakReference<View>) {
    class HolderLongClick(
        val holder: WeakReference<RecyclerViewHolder>,
        view: WeakReference<View>
    ) : LongClickEvent(view)

    class ItemLongClick(val item: RecyclerItem, view: WeakReference<View>) : LongClickEvent(view)
}
