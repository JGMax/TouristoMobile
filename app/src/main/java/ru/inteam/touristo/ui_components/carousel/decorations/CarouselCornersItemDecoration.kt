package ru.inteam.touristo.ui_components.carousel.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.inteam.touristo.R
import ru.inteam.touristo.common.ui.view.corners

class CarouselCornersItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        view.corners(parent.resources.getDimension(R.dimen.carousel_item_corner_radius))
    }
}
