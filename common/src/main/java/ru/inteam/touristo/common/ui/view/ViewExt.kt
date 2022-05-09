package ru.inteam.touristo.common.ui.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
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

fun View.margin(margin: Int) {
    val marginLayoutParams = ViewGroup.MarginLayoutParams(layoutParams)
    marginLayoutParams.setMargins(margin, margin, margin, margin)
    layoutParams = marginLayoutParams
}

fun View.scale(scale: Float) {
    ObjectAnimator.ofFloat(this, "scaleX", 1f, scale).apply {
        duration = 300
    }.start()
    ObjectAnimator.ofFloat(this, "scaleY", 1f, scale).apply {
        duration = 300
    }.start()
}
