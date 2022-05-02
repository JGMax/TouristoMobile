package ru.inteam.touristo.data.shared_media.content_resolver.presentation

import ru.inteam.touristo.data.shared_media.model.media.MediaResponse
import ru.inteam.touristo.data.shared_media.model.media.MediaSelector
import ru.inteam.touristo.data.state.LoadingState
import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.data.state.data

internal class CRMediaDataReducer :
    Reducer<CRMediaDataState, CRMediaDataEvent, Nothing, CRMediaDataOperation>() {

    override fun reduce(event: CRMediaDataEvent) {
        when (event) {
            is CRMediaDataEvent.GetMedia -> getMedia(event.mediaSelector)
            is CRMediaDataEvent.Media -> mediaState(event.mediaResponse)
            is CRMediaDataEvent.Update -> state.selector?.let { getMedia(it) }
                ?: noSelectorState()
        }
    }

    private fun noSelectorState() = state {
        copy(loadingState = LoadingState.Failed.Default(IllegalStateException("No selector")))
    }

    private fun mediaState(mediaResponse: List<MediaResponse>) = state {
        copy(loadingState = LoadingState.Loaded(mediaResponse))
    }

    private fun getMedia(mediaSelector: MediaSelector) {
        operations(CRMediaDataOperation.GetMedia(mediaSelector))
        state {
            val data = loadingState.data
            if (mediaSelector != state.selector || data == null) {
                copy(
                    selector = mediaSelector,
                    loadingState = LoadingState.Loading.Default()
                )
            } else {
                copy(loadingState = LoadingState.Loading.WithData(data))
            }
        }
    }
}
