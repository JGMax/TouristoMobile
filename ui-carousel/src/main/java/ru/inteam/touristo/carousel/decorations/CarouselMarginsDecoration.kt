package ru.inteam.touristo.carousel.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.inteam.touristo.carousel.R

internal class CarouselMarginsDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val outerVerticalMargin =
            parent.resources.getDimensionPixelSize(R.dimen.carousel_item_vertical_margin)
        val innerHorizontalMargin =
            parent.resources.getDimensionPixelSize(R.dimen.carousel_item_inner_horizontal_margin) / 2

        val contentLastPosition = (parent.adapter?.itemCount ?: 1) - 1
        val contentStartPosition = 0

        val viewHolder = parent.getChildViewHolder(view)

        val position = parent.getChildAdapterPosition(view)
            .takeIf { it != RecyclerView.NO_POSITION } ?: viewHolder.oldPosition

        outRect.apply {
            top = outerVerticalMargin
            bottom = outerVerticalMargin
        }
        when (position) {
            contentStartPosition -> outRect.apply {
                right = innerHorizontalMargin
            }
            contentLastPosition -> outRect.apply {
                left = innerHorizontalMargin
            }
            else -> outRect.apply {
                left = innerHorizontalMargin
                right = innerHorizontalMargin
            }
        }
    }
}
