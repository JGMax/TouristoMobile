package ru.inteam.touristo.common.data.shared_media.content_resolver.presentation

import ru.inteam.touristo.common.data.shared_media.model.media.MediaResponse
import ru.inteam.touristo.common.data.shared_media.model.media.MediaSelector
import ru.inteam.touristo.common.data.state.LoadingState

data class CRMediaDataState(
    val selector: MediaSelector? = null,
    val loadingState: LoadingState<List<MediaResponse>> = LoadingState.Loading.Default()
)
