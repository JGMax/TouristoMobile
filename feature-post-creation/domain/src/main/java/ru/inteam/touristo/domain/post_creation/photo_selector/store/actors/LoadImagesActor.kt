package ru.inteam.touristo.domain.post_creation.photo_selector.store.actors

import kotlinx.coroutines.flow.*
import ru.inteam.touristo.common.tea.Actor
import ru.inteam.touristo.common_media.shared_media.content_resolver.storage.CRMediaDataStorage
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorEvent
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorOperation

internal class LoadImagesActor(
    private val storage: CRMediaDataStorage
) : Actor<PhotoSelectorOperation, PhotoSelectorEvent> {

    override fun process(operations: Flow<PhotoSelectorOperation>): Flow<PhotoSelectorEvent> {
        return operations
            .filterIsInstance<PhotoSelectorOperation.Load>()
            .flatMapLatest { storage.data(it.selector) }
            .map(PhotoSelectorEvent::LoadingStatus)
    }
}
