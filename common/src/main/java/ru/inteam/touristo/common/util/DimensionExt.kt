package ru.inteam.touristo.common.util

import android.content.res.Resources
import android.util.TypedValue

val Int.px: Int
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

val Int.dp: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }
