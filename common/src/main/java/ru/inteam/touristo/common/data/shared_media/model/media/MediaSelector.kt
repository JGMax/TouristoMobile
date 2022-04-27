package ru.inteam.touristo.common.data.shared_media.model.media

import android.net.Uri

interface MediaSelector {
    val projection: List<MediaField<*>>
    val collection: Uri
    val sortOrder: SortOrder
    val selector: Selector
}

@JvmInline
value class SortOrder private constructor(val order: String?) {
    companion object {
        @JvmStatic
        fun asc(field: String): SortOrder = SortOrder("$field ASC")

        @JvmStatic
        fun desc(field: String): SortOrder = SortOrder("$field DESC")

        @JvmStatic
        val none: SortOrder = SortOrder(null)
    }
}

class Selector private constructor(val selection: String?, val args: Array<out String>?) {
    companion object {
        @JvmStatic
        fun createEqual(field: String, arg: String): Selector {
            return Selector("$field = ?", arrayOf(arg))
        }

        @JvmStatic
        val none = Selector(null, null)
    }
}
