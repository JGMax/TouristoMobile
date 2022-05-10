package ru.inteam.touristo.recycler.clicks

import kotlinx.coroutines.flow.Flow
import ru.inteam.touristo.recycler.item.RecyclerItem

interface ClicksOwner {
    val clicksManager: ClicksManager
}

inline fun <reified T : RecyclerItem> ClicksOwner.clicks(): Flow<ClickEvent.ItemClick> {
    return clicksManager.clicks<T>()
}

inline fun <reified T : RecyclerItem> ClicksOwner.clicks(viewId: Int): Flow<T> {
    return clicksManager.clicks(viewId)
}

inline fun <reified T : RecyclerItem> ClicksOwner.longClicks(): Flow<LongClickEvent.ItemLongClick> {
    return clicksManager.longClicks<T>()
}

inline fun <reified T : RecyclerItem> ClicksOwner.longClicks(viewId: Int): Flow<T> {
    return clicksManager.longClicks(viewId)
}
