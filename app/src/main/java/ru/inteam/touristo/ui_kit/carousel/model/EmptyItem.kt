package ru.inteam.touristo.ui_kit.carousel.model

import android.view.View
import ru.inteam.touristo.R
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem
import ru.inteam.touristo.databinding.UiKitCarouselItemEmptyBinding

data class EmptyItem(
    override val layoutId: Int = R.layout.ui_kit_carousel_item_empty
) : RecyclerItem<UiKitCarouselItemEmptyBinding, EmptyItem>() {

    override fun provideViewBinding(view: View): UiKitCarouselItemEmptyBinding {
        return UiKitCarouselItemEmptyBinding.bind(view)
    }
}
