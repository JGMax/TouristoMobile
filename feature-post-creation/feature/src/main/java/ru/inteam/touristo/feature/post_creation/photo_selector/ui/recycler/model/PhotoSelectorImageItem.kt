package ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.model

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import ru.inteam.touristo.common.ui.view.alpha
import ru.inteam.touristo.common.ui.view.scale
import ru.inteam.touristo.feature.post_creation.R
import ru.inteam.touristo.feature.post_creation.databinding.PhotoSelectorPhotoItemBinding
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.diff.PhotoSelectorDiffCallback.Companion.POSITION_PAYLOAD
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.diff.PhotoSelectorDiffCallback.Companion.SELECTED_PAYLOAD
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.diff.PhotoSelectorDiffCallback.Companion.SOURCE_PAYLOAD
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.holder.ViewTypeInitializer
import ru.inteam.touristo.recycler.holder.binding
import ru.inteam.touristo.recycler.item.RecyclerItem

internal data class PhotoSelectorImageItem(
    override val itemId: String,
    val needShowPosition: Boolean,
    val selectedPosition: Int,
    val source: Uri
) : RecyclerItem({ PhotoSelectorImageItemViewTypeInitializer() }) {

    override val layoutId: Int = R.layout.photo_selector_photo_item

    private val scale: Float
        get() = if (selectedPosition > 0) 0.8f else 1.0f
    private val alpha: Float
        get() = if (selectedPosition > 0) 0.55f else 1.0f

    override fun bind(holder: RecyclerViewHolder) = holder.binding<PhotoSelectorPhotoItemBinding> {
        image.scale(scale)
        image.alpha(alpha)
        position.setPosition(selectedPosition, needShowPosition)
        image.load(source)
    }

    override fun bind(
        holder: RecyclerViewHolder,
        payloads: MutableList<Any>
    ) = holder.binding<PhotoSelectorPhotoItemBinding> {
        val bundle = payloads.firstOrNull()

        if (bundle is Bundle) {
            if (bundle.getBoolean(SELECTED_PAYLOAD)) {
                image.scale(scale)
                image.alpha(alpha)
            }

            if (bundle.getBoolean(POSITION_PAYLOAD)) {
                position.setPosition(selectedPosition, needShowPosition)
            }

            if (bundle.getBoolean(SOURCE_PAYLOAD)) image.load(source)
        } else {
            super.bind(holder, payloads)
        }
    }

    private fun TextView.setPosition(selectedPosition: Int, needShowPosition: Boolean) {
        val needShow = needShowPosition && selectedPosition > 0

        if (needShow) text = selectedPosition.toString()
        isVisible = needShow
    }
}

internal class PhotoSelectorImageItemViewTypeInitializer : ViewTypeInitializer() {

    override fun init(
        holder: RecyclerViewHolder
    ) = holder.binding(PhotoSelectorPhotoItemBinding.bind(holder.itemView)) {
        clicks(image, holder)
    }
}
