package ru.inteam.touristo.common.ui.view.corners

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

class CornersOutlineProvider(
    private val radius: Float
) : ViewOutlineProvider() {

    override fun getOutline(view: View, outline: Outline) {
        val left = 0
        val top = 0
        val right = view.width
        val bottom = view.height
        outline.setRoundRect(left, top, right, bottom, radius)
    }
}
