package ru.inteam.touristo.carousel.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.inteam.touristo.recycler.util.getViewByAdapterPosition
import ru.inteam.touristo.carousel.R
import ru.inteam.touristo.ui_kit.images.ContentImageView

internal class CarouselMarginsItemDecoration : RecyclerView.ItemDecoration() {

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
                setFlexViewWidth(
                    flex = parent.getViewByAdapterPosition(0) ?: return@setOnLoadListener,
                    target = it,
                    parent = parent,
                    minMargin = outerHorizontalMargin
                )
            }
            contentLastPosition + 1 -> {
                val contentView = parent.getViewByAdapterPosition(contentLastPosition)
                (contentView as? ContentImageView)?.setOnLoadListener {
                    setFlexViewWidth(
                        flex = view,
                        target = it,
                        parent = parent,
                        minMargin = outerHorizontalMargin
                    )
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

    private fun setFlexViewWidth(flex: View, target: View, parent: RecyclerView, minMargin: Int) {
        val lp = flex.layoutParams
        lp.width = ((parent.width - target.width) / 2).coerceAtLeast(minMargin)
        flex.layoutParams = lp
    }
}
