package ru.inteam.touristo.ui_kit.carousel.model

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.inteam.touristo.R
import ru.inteam.touristo.common.ui.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.databinding.UiComponentCarouselItemBinding
import ru.inteam.touristo.ui_kit.carousel.CarouselLayoutManager

data class CarouselItem(
    val source: Uri
) : RecyclerItem<UiComponentCarouselItemBinding, CarouselItem>() {
    override val layoutId: Int = R.layout.ui_component_carousel_item

    override fun provideViewBinding(view: View): UiComponentCarouselItemBinding {
        return UiComponentCarouselItemBinding.bind(view)
    }

    override fun RecyclerViewHolder.initHolder(binding: UiComponentCarouselItemBinding) {
        clicks(itemView.clicks(), this)
    }

    override fun UiComponentCarouselItemBinding.bind(me: CarouselItem) {
        image.load(source)
    }
}
