package ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.diff

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.model.PhotoSelectorImageItem
import ru.inteam.touristo.recycler.item.RecyclerItem

internal class PhotoSelectorDiffCallback : DiffUtil.ItemCallback<RecyclerItem>() {

    companion object {
        const val SOURCE_PAYLOAD = "SOURCE_PAYLOAD"
        const val SELECTED_PAYLOAD = "SELECTED_PAYLOAD"
        const val POSITION_PAYLOAD = "POSITION_PAYLOAD"
    }

    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return if (oldItem is PhotoSelectorImageItem && newItem is PhotoSelectorImageItem) {
            oldItem == newItem
        } else {
            false
        }
    }

    override fun getChangePayload(oldItem: RecyclerItem, newItem: RecyclerItem): Any {
        val payloads = Bundle()
        if (oldItem is PhotoSelectorImageItem && newItem is PhotoSelectorImageItem) {
            payloads.putBoolean(SOURCE_PAYLOAD, oldItem.source != newItem.source)

            val isSelectedChanged = oldItem.selectedPosition * newItem.selectedPosition <= 0
            payloads.putBoolean(SELECTED_PAYLOAD, isSelectedChanged)

            val arePositionsChanged = oldItem.selectedPosition != newItem.selectedPosition
            val areShowPositionChanged = oldItem.needShowPosition != newItem.needShowPosition
            payloads.putBoolean(POSITION_PAYLOAD, arePositionsChanged || areShowPositionChanged)
        }
        return payloads
    }
}
