package ru.inteam.touristo.common.ui.view

import android.graphics.Bitmap
import android.view.ViewGroup.LayoutParams
import ru.inteam.touristo.common.util.px
import kotlin.math.roundToInt

fun Bitmap.getViewLayoutParamsByMaxWidth(maxWidth: Int): LayoutParams {
    val desiredWidth = width.coerceAtMost(maxWidth)
    val k = desiredWidth / width.toDouble()
    val desiredHeight = (height * k).roundToInt()
    return LayoutParams(desiredWidth, desiredHeight)
}

fun Bitmap.getViewLayoutParamsByMaxHeight(maxHeight: Int): LayoutParams {
    val desiredHeight = height.coerceAtMost(maxHeight)
    val k = desiredHeight / height.toDouble()
    val desiredWidth = (width * k).roundToInt()
    return LayoutParams(desiredWidth, desiredHeight)
}
