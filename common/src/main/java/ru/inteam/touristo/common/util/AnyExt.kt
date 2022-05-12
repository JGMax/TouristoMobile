package ru.inteam.touristo.common.util

import java.lang.ref.WeakReference

fun<T> T.weak(): WeakReference<T> {
    return WeakReference(this)
}
