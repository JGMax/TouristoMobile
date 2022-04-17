package ru.inteam.touristo.common.util

import ru.inteam.touristo.common.data.shared_media.model.media.MediaField

fun <T> MutableCollection<T>.addIfNotExists(value: T): MutableCollection<T> {
    if (value !in this) {
        add(value)
    }
    return this
}

inline fun <reified T : Any> Map<MediaField<*>, *>.getFieldValue(mediaField: MediaField<T>): T? {
    return get(mediaField) as? T
}

context(Map<String, Any>)
        inline fun <reified T : Any> String.value(): T {
    return get(this) as T
}
