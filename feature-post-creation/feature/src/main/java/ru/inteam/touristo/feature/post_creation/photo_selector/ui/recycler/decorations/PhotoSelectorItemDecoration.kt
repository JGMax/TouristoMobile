package ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.inteam.touristo.common.util.getDimensionPixelSize
import ru.inteam.touristo.feature.post_creation.R

internal class PhotoSelectorItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val offset = view.context.getDimensionPixelSize(R.dimen.photo_selector_grid_image_offset)
        val position = parent.getChildAdapterPosition(view)
        val spanCount = (parent.layoutManager as? GridLayoutManager)?.spanCount ?: 1

        outRect.apply {
            when {
                position % spanCount == 0 -> {
                    left = offset
                    right = offset
                }
                else -> right = offset
            }

            when {
                position < spanCount -> {
                    top = offset
                    bottom = offset
                }
                else -> bottom = offset
            }
        }
    }
}
