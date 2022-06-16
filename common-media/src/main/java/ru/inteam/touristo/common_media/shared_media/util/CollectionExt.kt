package ru.inteam.touristo.common_media.shared_media.util

import ru.inteam.touristo.common_media.shared_media.model.media.CRField

fun <T> MutableCollection<T>.addIfNotExists(value: T): MutableCollection<T> {
    if (value !in this) {
        add(value)
    }
    return this
}

inline fun <reified T : Any> Map<CRField<*>, *>.getFieldValue(mediaField: CRField<T>): T? {
    return get(mediaField) as? T
}
