package ru.inteam.touristo.carousel.model

import android.net.Uri
import ru.inteam.touristo.carousel.R
import ru.inteam.touristo.carousel.databinding.UiCarouselItemBinding
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.holder.ViewTypeInitializer
import ru.inteam.touristo.recycler.holder.binding
import ru.inteam.touristo.recycler.item.RecyclerItem

data class CarouselItem(
    override val itemId: String,
    val source: Uri
) : RecyclerItem({ CarouselItemViewTypeInitializer() }) {
    override val layoutId: Int = R.layout.ui_carousel_item

    override fun bind(holder: RecyclerViewHolder) = holder.binding<UiCarouselItemBinding> {
        carouselImage.load(source)
    }
}

internal class CarouselItemViewTypeInitializer : ViewTypeInitializer() {

    override fun init(
        holder: RecyclerViewHolder
    ) = holder.binding(UiCarouselItemBinding.bind(holder.itemView)) {
        clicks(carouselImage, holder)
    }
}
