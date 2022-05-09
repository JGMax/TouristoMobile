package ru.inteam.touristo.domain.store

import ru.inteam.touristo.common_media.shared_media.model.media.MediaSelector

sealed class PhotoSelectorOperation {
    internal class Load(val selector: MediaSelector) : PhotoSelectorOperation()
}
