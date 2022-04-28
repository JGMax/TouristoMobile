package ru.inteam.touristo.ui_kit.carousel.model

import ru.inteam.touristo.ui_kit.R
import ru.inteam.touristo.ui_kit.databinding.UiKitCarouselItemFlexBinding
import ru.inteam.touristo.ui_kit.recycler.item.RecyclerItem

internal class FlexItem(
    override val layoutId: Int = R.layout.ui_kit_carousel_item_flex
) : RecyclerItem<UiKitCarouselItemFlexBinding, FlexItem>()
