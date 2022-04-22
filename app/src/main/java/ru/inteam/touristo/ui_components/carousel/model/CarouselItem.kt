package ru.inteam.touristo.ui_components.carousel.model

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup.LayoutParams
import ru.inteam.touristo.R
import ru.inteam.touristo.common.ui.recycler.holder.RecyclerViewHolder
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem
import ru.inteam.touristo.common.ui.view.context
import ru.inteam.touristo.common.ui.view.getViewLayoutParamsByMaxHeight
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.common.util.getDimensionPixelSize
import ru.inteam.touristo.databinding.UiComponentCarouselItemBinding

data class CarouselItem(
    val source: () -> Bitmap
) : RecyclerItem<UiComponentCarouselItemBinding, CarouselItem>() {
    override val layoutId: Int = R.layout.ui_component_carousel_item
    private var maxHeight: Int = 0
    private var verticalMargins: Int = 0
    private val bitmap: Bitmap by lazy(source)
    private var layoutParams: LayoutParams? = null

    override fun provideViewBinding(view: View): UiComponentCarouselItemBinding {
        return UiComponentCarouselItemBinding.bind(view)
    }

    override fun RecyclerViewHolder.initHolder(binding: UiComponentCarouselItemBinding) {
        clicks(itemView.clicks(), this)
    }

    override fun UiComponentCarouselItemBinding.bind(me: CarouselItem) {
        if (layoutParams == null) {
            maxHeight = context.getDimensionPixelSize(R.dimen.carousel_height)
            verticalMargins = context.getDimensionPixelSize(R.dimen.carousel_item_vertical_margin) * 2
            layoutParams = bitmap.getViewLayoutParamsByMaxHeight(maxHeight - verticalMargins)
        }
        root.layoutParams = layoutParams
        image.setImageBitmap(bitmap)
    }
}
