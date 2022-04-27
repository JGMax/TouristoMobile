package ru.inteam.touristo.common.data.shared_media.content_resolver.presentation

import ru.inteam.touristo.common.data.shared_media.model.media.MediaResponse
import ru.inteam.touristo.common.data.shared_media.model.media.MediaSelector

sealed class CRMediaDataEvent {
    class GetMedia(val mediaSelector: MediaSelector) : CRMediaDataEvent()
    class Media(val mediaResponse: List<MediaResponse>) : CRMediaDataEvent()

    object Update : CRMediaDataEvent()
}
