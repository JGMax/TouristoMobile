package ru.inteam.touristo.common.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment

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

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, length).show()
}
