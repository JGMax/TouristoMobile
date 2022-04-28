package ru.inteam.touristo.common.ui.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.getViewByAdapterPosition(position: Int): View? {
    return layoutManager?.findViewByPosition(position)
}
