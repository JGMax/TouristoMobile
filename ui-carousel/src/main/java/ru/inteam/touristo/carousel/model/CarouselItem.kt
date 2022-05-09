package ru.inteam.touristo.carousel.model

import android.net.Uri
import android.view.View
import ru.inteam.touristo.carousel.R
import ru.inteam.touristo.carousel.databinding.UiKitCarouselItemBinding
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.item.RecyclerItem

data class CarouselItem(
    val source: Uri
) : RecyclerItem<UiKitCarouselItemBinding, CarouselItem>() {
    override val layoutId: Int = R.layout.ui_kit_carousel_item

    override fun provideViewBinding(view: View): UiKitCarouselItemBinding {
        return UiKitCarouselItemBinding.bind(view)
    }

    override fun RecyclerViewHolder.initHolder(binding: UiKitCarouselItemBinding) {
        clicks(itemView.clicks(), this)
    }

    override fun UiKitCarouselItemBinding.bind(me: CarouselItem) {
        image.load(source)
    }
}
