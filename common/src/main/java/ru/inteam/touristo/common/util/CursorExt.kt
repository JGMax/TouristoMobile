package ru.inteam.touristo.common.util

import android.database.Cursor
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun <T : Any> Cursor.get(columnIndex: Int, kClass: KClass<T>): T {
    return when (kClass) {
        Int::class -> getInt(columnIndex)
        String::class -> getString(columnIndex)
        Double::class -> getDouble(columnIndex)
        Float::class -> getFloat(columnIndex)
        Long::class -> getLong(columnIndex)
        Short::class -> getShort(columnIndex)
        else -> getBlob(columnIndex)
    } as T
}
