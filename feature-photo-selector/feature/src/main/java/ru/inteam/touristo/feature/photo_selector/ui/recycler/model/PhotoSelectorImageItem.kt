package ru.inteam.touristo.feature.photo_selector.ui.recycler.model

import android.net.Uri
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.common.ui.view.scale
import ru.inteam.touristo.feature.photo_selector.R
import ru.inteam.touristo.feature.photo_selector.databinding.PhotoSelectorPhotoItemBinding
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.holder.ViewType
import ru.inteam.touristo.recycler.holder.binding
import ru.inteam.touristo.recycler.item.RecyclerItem

internal data class PhotoSelectorImageItem(
    override val itemId: String,
    val isSelected: Boolean,
    val source: Uri
) : RecyclerItem() {

    override val layoutId: Int = R.layout.photo_selector_photo_item
    private val scale: Float
        get() = if (isSelected) 0.8f else 1.0f

    override fun bind(holder: RecyclerViewHolder) = holder.binding<PhotoSelectorPhotoItemBinding> {
        image.scale(scale)
        image.load(source)
        println("xxx: bind $holder")
    }
}

internal class PhotoSelectorImageItemViewType : ViewType() {

    override fun init(
        holder: RecyclerViewHolder
    ) = holder.binding(PhotoSelectorPhotoItemBinding.bind(holder.itemView)) {
        clicks(image.clicks(), holder)
        println("xxx: $holder")
    }
}
