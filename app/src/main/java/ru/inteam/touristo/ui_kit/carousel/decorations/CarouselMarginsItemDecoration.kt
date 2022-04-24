package ru.inteam.touristo.ui_kit.carousel.decorations

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.inteam.touristo.R

class CarouselMarginsItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val outerVerticalMargin =
            parent.resources.getDimensionPixelSize(R.dimen.carousel_item_vertical_margin)
        val outerHorizontalMargin =
            parent.resources.getDimensionPixelSize(R.dimen.carousel_item_outer_horizontal_margin)
        val innerHorizontalMargin =
            parent.resources.getDimensionPixelSize(R.dimen.carousel_item_inner_horizontal_margin) / 2

        val lastPosition = (parent.adapter?.itemCount ?: 1) - 1

        val position = parent.getChildAdapterPosition(view)
        Log.e("d", "d")

        outRect.apply {
            top = outerVerticalMargin
            bottom = outerVerticalMargin
        }
        when (position) {
            RecyclerView.NO_POSITION -> outRect.apply {
                left = 0
                right = 0
            }
            0 -> outRect.apply {
                left = outerHorizontalMargin
                right = innerHorizontalMargin
            }
            lastPosition -> outRect.apply {
                left = innerHorizontalMargin
                right = outerHorizontalMargin
            }
            else -> outRect.apply {
                left = innerHorizontalMargin
                right = innerHorizontalMargin
            }
        }
    }
}
