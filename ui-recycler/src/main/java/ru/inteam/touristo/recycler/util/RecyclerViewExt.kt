package ru.inteam.touristo.recycler.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.getViewByAdapterPosition(position: Int): View? {
    return layoutManager?.findViewByPosition(position)
}
