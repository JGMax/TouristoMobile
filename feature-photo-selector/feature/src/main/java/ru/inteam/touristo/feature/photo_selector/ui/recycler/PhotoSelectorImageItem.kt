package ru.inteam.touristo.feature.photo_selector.ui.recycler

import android.net.Uri
import android.view.View
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.common.ui.view.scale
import ru.inteam.touristo.feature.photo_selector.R
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.item.RecyclerItem
import ru.inteam.touristo.feature.photo_selector.databinding.PhotoSelectorPhotoItemBinding as Binding

internal data class PhotoSelectorImageItem(
    override val itemId: String,
    val isSelected: Boolean,
    val source: Uri
) : RecyclerItem<Binding, PhotoSelectorImageItem>() {

    override val layoutId: Int = R.layout.photo_selector_photo_item
    private val scale: Float
        get() = if (isSelected) 0.8f else 1.0f

    override fun provideViewBinding(view: View): Binding {
        return Binding.bind(view)
    }

    override fun RecyclerViewHolder.initHolder(binding: Binding) {
        clicks(binding.image.clicks(), this)
    }

    override fun Binding.bind(me: PhotoSelectorImageItem) {
        image.scale(scale)
        image.load(source)
    }
}
