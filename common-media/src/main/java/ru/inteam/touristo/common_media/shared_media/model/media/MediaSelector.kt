package ru.inteam.touristo.common_media.shared_media.model.media

import android.net.Uri

interface MediaSelector {
    val projection: List<CRField<*>>
    val collection: Uri
    val sortOrder: SortOrder
    val selector: Selector
}

@JvmInline
value class SortOrder private constructor(val order: String?) {
    companion object {
        @JvmStatic
        fun asc(field: CRField<*>): SortOrder = SortOrder("${field.value} ASC")

        @JvmStatic
        fun desc(field: CRField<*>): SortOrder = SortOrder("${field.value} DESC")

        @JvmStatic
        val none: SortOrder = SortOrder(null)
    }
}

class Selector private constructor(val selection: String?, val args: Array<out String>?) {
    companion object {
        @JvmStatic
        fun createEqual(field: CRField<*>, arg: String): Selector {
            return Selector("${field.value} = ?", arrayOf(arg))
        }

        @JvmStatic
        val none = Selector(null, null)
    }
}
