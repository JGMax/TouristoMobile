package ru.inteam.touristo.ui_kit.carousel.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.inteam.touristo.ui_kit.R
import ru.inteam.touristo.ui_kit.images.ContentImageView

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

        val contentLastPosition = (parent.adapter?.itemCount ?: 2) - 2
        val contentStartPosition = 1

        val position = parent.getChildAdapterPosition(view)

        when (position) {
            contentStartPosition -> (view as? ContentImageView)?.setOnLoadListener {
                val empty = parent.getChildAt(0) ?: return@setOnLoadListener
                val lp = empty.layoutParams
                lp.width = (parent.width - it.width) / 2
                empty.layoutParams = lp
            }
            contentLastPosition + 1 -> {
                val contentView = parent.getChildAt(contentLastPosition)
                (contentView as? ContentImageView)?.setOnLoadListener {
                    val lp = view.layoutParams
                    lp.width = (parent.width - it.width) / 2
                    view.layoutParams = lp
                }
            }
        }

        outRect.apply {
            top = outerVerticalMargin
            bottom = outerVerticalMargin
        }
        when (position) {
            RecyclerView.NO_POSITION,
            contentLastPosition + 1,
            contentStartPosition - 1 -> outRect.apply {
                left = 0
                right = 0
            }
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
