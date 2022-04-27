package ru.inteam.touristo.common.data.shared_media.content_resolver.presentation

import ru.inteam.touristo.common.data.shared_media.model.media.MediaSelector

sealed class CRMediaDataOperation {
    class GetMedia(val mediaSelector: MediaSelector) : CRMediaDataOperation()
}
