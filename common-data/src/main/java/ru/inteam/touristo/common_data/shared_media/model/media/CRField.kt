package ru.inteam.touristo.common_data.shared_media.model.media

import android.provider.BaseColumns
import android.provider.MediaStore.Images.ImageColumns
import android.provider.MediaStore.MediaColumns
import kotlin.reflect.KClass

open class CRField<T : Any>(val value: String, val kClass: KClass<T>) {

    object _ID : CRField<Long>(BaseColumns._ID, Long::class)
    object _COUNT : CRField<Int>(BaseColumns._COUNT, Int::class)

    open class MediaField<T : Any>(value: String, kClass: KClass<T>) : CRField<T>(value, kClass) {
        object DATE_ADDED : MediaField<Int>(MediaColumns.DATE_ADDED, Int::class)
        object DATE_MODIFIED : MediaField<Int>(MediaColumns.DATE_MODIFIED, Int::class)

        object HEIGHT : MediaField<Int>(MediaColumns.HEIGHT, Int::class)
        object WIDTH : MediaField<Int>(MediaColumns.WIDTH, Int::class)
        object SIZE : MediaField<Int>(MediaColumns.SIZE, Int::class)

        object DISPLAY_NAME : MediaField<String>(MediaColumns.DISPLAY_NAME, String::class)
        object MIME_TYPE : MediaField<String>(MediaColumns.MIME_TYPE, String::class)
        object TITLE : MediaField<String>(MediaColumns.TITLE, String::class)

        open class ImageField<T : Any>(
            value: String,
            kClass: KClass<T>
        ) : MediaField<T>(value, kClass) {
            object DESCRIPTION : ImageField<String>(ImageColumns.DESCRIPTION, String::class)
            object IS_PRIVATE : ImageField<Int>(ImageColumns.IS_PRIVATE, Int::class)
        }
    }
}
