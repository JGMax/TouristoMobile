package ru.inteam.touristo.carousel.model

import android.net.Uri
import ru.inteam.touristo.carousel.R
import ru.inteam.touristo.carousel.databinding.UiCarouselItemBinding
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.recycler.holder.ViewType
import ru.inteam.touristo.recycler.holder.binding
import ru.inteam.touristo.recycler.item.RecyclerItem

data class CarouselItem(
    val source: Uri
) : RecyclerItem() {
    override val layoutId: Int = R.layout.ui_carousel_item

    override fun bind(holder: RecyclerViewHolder) = holder.binding<UiCarouselItemBinding> {
        carouselImage.load(source)
    }
}

internal class CarouselItemViewType : ViewType() {

    override fun init(
        holder: RecyclerViewHolder
    ) = holder.binding(UiCarouselItemBinding.bind(holder.itemView)) {
        clicks(carouselImage, holder)
    }
}
