package ru.inteam.touristo.common_data.shared_media.util

import ru.inteam.touristo.common_data.shared_media.model.media.CRField

fun <T> MutableCollection<T>.addIfNotExists(value: T): MutableCollection<T> {
    if (value !in this) {
        add(value)
    }
    return this
}

inline fun <reified T : Any> Map<CRField<*>, *>.getFieldValue(mediaField: CRField<T>): T? {
    return get(mediaField) as? T
}

context(Map<String, Any>)
        inline fun <reified T : Any> String.value(): T {
    return get(this) as T
}
