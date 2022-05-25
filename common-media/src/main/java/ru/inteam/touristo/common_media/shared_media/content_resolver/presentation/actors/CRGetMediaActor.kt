package ru.inteam.touristo.common_media.shared_media.content_resolver.presentation.actors

import android.content.ContentResolver
import android.content.ContentUris
import kotlinx.coroutines.flow.*
import ru.inteam.touristo.common.tea.Actor
import ru.inteam.touristo.common.util.get
import ru.inteam.touristo.common_media.shared_media.content_resolver.presentation.CRMediaDataEvent
import ru.inteam.touristo.common_media.shared_media.content_resolver.presentation.CRMediaDataOperation
import ru.inteam.touristo.common_media.shared_media.model.media.CRField
import ru.inteam.touristo.common_media.shared_media.model.media.MediaResponse
import ru.inteam.touristo.common_media.shared_media.model.media.MediaSelector
import ru.inteam.touristo.common_media.shared_media.util.addIfNotExists

internal class CRGetMediaActor(
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
            .addIfNotExists(CRField._ID)
        val mediaCollection = mediaSelector.limit.selection?.let {
            mediaSelector.collection
                .buildUpon()
                .encodedQuery(it)
                .build()
        } ?: mediaSelector.collection

        val query = contentResolver.query(
            mediaCollection,
            fullProjection.map(CRField<*>::value).toTypedArray(),
            mediaSelector.selector.selection,
            mediaSelector.selector.args,
            mediaSelector.sortOrder.order
        )

        val result = mutableListOf<MediaResponse>()
        query?.use { cursor ->
            val columnsMap = HashMap<CRField<*>, Int>()

            fullProjection.forEach {
                columnsMap[it] = cursor.getColumnIndexOrThrow(it.value)
            }

            while (cursor.moveToNext()) {
                val id = cursor.get(columnsMap[CRField._ID]!!, CRField._ID.kClass)
                val contentUri = ContentUris.withAppendedId(mediaSelector.collection, id)

                val fieldsMap = HashMap<CRField<*>, Any>()
                for ((field, column) in columnsMap) {
                    if (field != CRField._ID) {
                        fieldsMap[field] = cursor.get(column, field.kClass)
                    }
                }
                result.add(MediaResponse(id, contentUri, fieldsMap))
            }
        }
        return result
    }
}
