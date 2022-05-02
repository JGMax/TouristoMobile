package ru.inteam.touristo.data.shared_media.content_resolver.presentation

import ru.inteam.touristo.data.shared_media.model.media.MediaResponse
import ru.inteam.touristo.data.shared_media.model.media.MediaSelector

internal sealed class CRMediaDataEvent {
    class GetMedia(val mediaSelector: MediaSelector) : CRMediaDataEvent()
    class Media(val mediaResponse: List<MediaResponse>) : CRMediaDataEvent()

    object Update : CRMediaDataEvent()
}
