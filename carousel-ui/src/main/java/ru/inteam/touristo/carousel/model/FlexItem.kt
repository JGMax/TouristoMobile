package ru.inteam.touristo.carousel.model

import ru.inteam.touristo.carousel.R
import ru.inteam.touristo.carousel.databinding.UiKitCarouselItemFlexBinding
import ru.inteam.touristo.recycler.item.RecyclerItem

internal class FlexItem(
    override val layoutId: Int = R.layout.ui_kit_carousel_item_flex
) : RecyclerItem<UiKitCarouselItemFlexBinding, FlexItem>()
