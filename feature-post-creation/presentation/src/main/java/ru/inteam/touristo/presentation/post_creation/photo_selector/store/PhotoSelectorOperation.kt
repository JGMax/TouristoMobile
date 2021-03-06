package ru.inteam.touristo.presentation.post_creation.photo_selector.store

import ru.inteam.touristo.common_media.shared_media.model.media.MediaSelector

sealed class PhotoSelectorOperation {
    internal class Load(val selector: MediaSelector) : PhotoSelectorOperation()
    internal class LoadBuckets(val selector: MediaSelector) : PhotoSelectorOperation()
}
