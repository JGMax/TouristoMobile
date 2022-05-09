package ru.inteam.touristo.carousel.layout_manager

import android.content.Context
import android.view.View
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.inteam.touristo.carousel.R

open class CarouselLayoutManager(
    context: Context
) : LinearLayoutManager(context, HORIZONTAL, false) {

    init {
        initialPrefetchItemCount = 4
    }

    private lateinit var recyclerView: RecyclerView

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        // always measure first item, its size determines starting offset
        // this must be done before super.onLayoutChildren
        if (childCount == 0 && state.itemCount > 0) {
            val firstChild = recycler.getViewForPosition(0)
            measureChildWithMargins(firstChild, 0, 0)
            recycler.recycleView(firstChild)
        }
        super.onLayoutChildren(recycler, state)
    }

    override fun measureChildWithMargins(child: View, widthUsed: Int, heightUsed: Int) {
        val lp = (child.layoutParams as RecyclerView.LayoutParams)
            .absoluteAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
            ?: recyclerView.getChildViewHolder(child).oldPosition

        super.measureChildWithMargins(child, widthUsed, heightUsed)
        if (lp != 0 && lp != itemCount - 1) return
        val measuredWidth = getDecoratedMeasuredWidth(child)
        // after determining first and/or last items size use it to alter host padding
        val hPadding = ((width - measuredWidth) / 2)
            .coerceAtLeast(child.resources.getDimensionPixelSize(R.dimen.carousel_item_outer_horizontal_margin))
        recyclerView.updatePaddingRelative(start = hPadding, end = hPadding)
    }

    // capture host recyclerview
    override fun onAttachedToWindow(view: RecyclerView) {
        recyclerView = view
        super.onAttachedToWindow(view)
    }
}
