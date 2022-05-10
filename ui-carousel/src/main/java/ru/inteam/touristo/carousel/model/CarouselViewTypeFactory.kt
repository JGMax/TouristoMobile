package ru.inteam.touristo.carousel.model

import ru.inteam.touristo.carousel.R
import ru.inteam.touristo.recycler.holder.ViewType
import ru.inteam.touristo.recycler.holder.ViewTypeFactory

internal class CarouselViewTypeFactory : ViewTypeFactory {

    override fun createViewType(viewType: Int): ViewType? {
        return when(viewType) {
            R.layout.ui_carousel_item -> CarouselItemViewType()
            else -> null
        }
    }
}
