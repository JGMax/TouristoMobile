package ru.inteam.touristo.common.data.shared_media.model.media

import android.net.Uri

open class MediaResponse(
    val id: Long,
    val contentUri: Uri,
    val fields: Map<MediaField<*>, Any> = emptyMap()
)
