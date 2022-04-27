package ru.inteam.touristo.ui_kit.carousel.model

import ru.inteam.touristo.ui_kit.R
import ru.inteam.touristo.ui_kit.recycler.item.RecyclerItem
import ru.inteam.touristo.ui_kit.databinding.UiKitCarouselItemEmptyBinding

class EmptyItem(
    override val layoutId: Int = R.layout.ui_kit_carousel_item_empty
) : RecyclerItem<UiKitCarouselItemEmptyBinding, EmptyItem>()
