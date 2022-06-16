package ru.inteam.touristo.common_data.util

context(Map<String, Any>)
inline fun <reified T : Any> String.value(): T {
    return get(this) as T
}
