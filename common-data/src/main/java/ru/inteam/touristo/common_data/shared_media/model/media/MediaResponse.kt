package ru.inteam.touristo.common_data.shared_media.model.media

import android.net.Uri

open class MediaResponse(
    val id: Long,
    val contentUri: Uri,
    val fields: Map<CRField<*>, Any> = emptyMap()
)
