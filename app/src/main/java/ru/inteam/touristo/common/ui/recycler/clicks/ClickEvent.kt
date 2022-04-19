package ru.inteam.touristo.common.ui.recycler.clicks

import android.view.View
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem
import java.lang.ref.WeakReference

data class ClickEvent(val item: RecyclerItem<*, *>, val view: WeakReference<View>)

data class LongClickEvent(val item: RecyclerItem<*, *>, val view: WeakReference<View>)
