package ru.inteam.touristo.common.data.shared_media.content_resolver.presentation.actors

import android.content.ContentResolver
import android.content.ContentUris
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.inteam.touristo.common.data.shared_media.content_resolver.presentation.CRMediaDataOperation
import ru.inteam.touristo.common.data.shared_media.content_resolver.presentation.CRMediaDataEvent
import ru.inteam.touristo.common.data.shared_media.model.media.MediaField
import ru.inteam.touristo.common.data.shared_media.model.media.MediaResponse
import ru.inteam.touristo.common.data.shared_media.model.media.MediaSelector
import ru.inteam.touristo.common.tea.Actor
import ru.inteam.touristo.common.util.addIfNotExists
import ru.inteam.touristo.common.util.get

class CRGetMediaActor(
    private val contentResolver: ContentResolver
) : Actor<CRMediaDataOperation, CRMediaDataEvent> {
    override fun process(operations: Flow<CRMediaDataOperation>): Flow<CRMediaDataEvent> {
        return operations
            .filterIsInstance<CRMediaDataOperation.GetMedia>()
            .mapLatest { getMedia(it.mediaSelector) }
            .map(CRMediaDataEvent::Media)
    }

    private fun getMedia(mediaSelector: MediaSelector): List<MediaResponse> {
        val fullProjection = mediaSelector.projection.toMutableList()
            .addIfNotExists(MediaField._ID)

        val query = contentResolver.query(
            mediaSelector.collection,
            fullProjection.map(MediaField<*>::value).toTypedArray(),
            mediaSelector.selector.selection,
            mediaSelector.selector.args,
            mediaSelector.sortOrder.order
        )

        val result = mutableListOf<MediaResponse>()
        query?.use { cursor ->
            val columnsMap = HashMap<MediaField<*>, Int>()

            fullProjection.forEach {
                columnsMap[it] = cursor.getColumnIndexOrThrow(it.value)
            }

            while (cursor.moveToNext()) {
                val id = cursor.get(columnsMap[MediaField._ID]!!, MediaField._ID.kClass)
                val contentUri = ContentUris.withAppendedId(mediaSelector.collection, id)

                val fieldsMap = HashMap<MediaField<*>, Any>()
                for ((field, column) in columnsMap) {
                    if (field != MediaField._ID) {
                        fieldsMap[field] = cursor.get(column, field.kClass)
                    }
                }
                result.add(MediaResponse(id, contentUri, fieldsMap))
            }
        }
        return result
    }
}
