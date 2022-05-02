package ru.inteam.touristo.data.shared_media.content_resolver.presentation

import ru.inteam.touristo.data.shared_media.model.media.MediaSelector

internal sealed class CRMediaDataOperation {
    class GetMedia(val mediaSelector: MediaSelector) : CRMediaDataOperation()
}
