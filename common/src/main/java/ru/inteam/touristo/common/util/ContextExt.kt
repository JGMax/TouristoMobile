package ru.inteam.touristo.common.util

import android.content.Context
import androidx.annotation.DimenRes

val Context.displayWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.displayHeight: Int
    get() = resources.displayMetrics.heightPixels

fun Context.getDimensionPixelSize(@DimenRes resId: Int): Int {
    return resources.getDimensionPixelSize(resId)
}

inline fun<reified T: Any> Context.systemService(): T {
    return getSystemService(T::class.java)
}
