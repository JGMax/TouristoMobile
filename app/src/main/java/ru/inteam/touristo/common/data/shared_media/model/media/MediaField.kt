package ru.inteam.touristo.common.data.shared_media.model.media

import android.provider.BaseColumns
import android.provider.MediaStore.Images.ImageColumns
import android.provider.MediaStore.MediaColumns
import kotlin.reflect.KClass

sealed class MediaField<T : Any>(val value: String, val kClass: KClass<T>) {
    /**
     * Base field
     */
    object _ID : MediaField<Long>(BaseColumns._ID, Long::class)
    object _COUNT : MediaField<Int>(BaseColumns._COUNT, Int::class)

    /**
     * Media field
     */
    object DATE_ADDED : MediaField<Int>(MediaColumns.DATE_ADDED, Int::class)
    object DATE_MODIFIED : MediaField<Int>(MediaColumns.DATE_MODIFIED, Int::class)

    object HEIGHT : MediaField<Int>(MediaColumns.HEIGHT, Int::class)
    object WIDTH : MediaField<Int>(MediaColumns.WIDTH, Int::class)
    object SIZE : MediaField<Int>(MediaColumns.SIZE, Int::class)

    object DISPLAY_NAME : MediaField<String>(MediaColumns.DISPLAY_NAME, String::class)
    object MIME_TYPE : MediaField<String>(MediaColumns.MIME_TYPE, String::class)
    object TITLE : MediaField<String>(MediaColumns.TITLE, String::class)

    /**
     * Image media filed
     */
    object DESCRIPTION : MediaField<String>(ImageColumns.DESCRIPTION, String::class)
    object IS_PRIVATE : MediaField<Int>(ImageColumns.IS_PRIVATE, Int::class)
}
