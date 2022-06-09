package ru.inteam.touristo.presentation.post_creation.photo_selector.store.actors

import kotlinx.coroutines.flow.*
import ru.inteam.touristo.common.tea.Actor
import ru.inteam.touristo.common_data.state.map
import ru.inteam.touristo.common_media.shared_media.content_resolver.storage.CRMediaDataStorage
import ru.inteam.touristo.common_media.shared_media.model.media.CRField
import ru.inteam.touristo.common_media.shared_media.util.getFieldValue
import ru.inteam.touristo.presentation.post_creation.photo_selector.store.PhotoSelectorEvent
import ru.inteam.touristo.presentation.post_creation.photo_selector.store.PhotoSelectorOperation

internal class LoadBucketsActor(
    private val storage: CRMediaDataStorage
) : Actor<PhotoSelectorOperation, PhotoSelectorEvent> {

    override fun process(operations: Flow<PhotoSelectorOperation>): Flow<PhotoSelectorEvent> {
        return operations
            .filterIsInstance<PhotoSelectorOperation.LoadBuckets>()
            .flatMapLatest { storage.data(it.selector) }
            .map { state ->
                state.map { list ->
                    list.groupBy { it.fields.getFieldValue(CRField.MediaField.BUCKET_DISPLAY_NAME) }
                        .keys
                        .sortedBy { it }
                        .toList()
                }
            }
            .map(PhotoSelectorEvent::LoadingStatusBuckets)
    }
}
