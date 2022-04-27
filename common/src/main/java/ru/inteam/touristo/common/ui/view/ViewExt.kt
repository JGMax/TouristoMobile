package ru.inteam.touristo.common.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import ru.inteam.touristo.common.ui.view.corners.CornersOutlineProvider

fun ViewGroup.inflate(@LayoutRes layoutId: Int): View {
    val layoutInflater = LayoutInflater.from(context)
    return layoutInflater.inflate(layoutId, this, false)
}

fun View.corners(radius: Float) {
    outlineProvider = CornersOutlineProvider(radius)
    clipToOutline = true
}
