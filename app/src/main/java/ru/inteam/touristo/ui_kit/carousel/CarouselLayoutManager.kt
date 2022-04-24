package ru.inteam.touristo.ui_kit.carousel

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarouselLayoutManager(context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
    }
}
