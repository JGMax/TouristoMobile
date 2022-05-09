package ru.inteam.touristo.domain.store

import android.net.Uri
import ru.inteam.touristo.common_data.state.LoadingState
import ru.inteam.touristo.common_media.shared_media.model.media.MediaResponse

sealed class PhotoSelectorEvent {
    internal class LoadingStatus(
        val loadingState: LoadingState<List<MediaResponse>>
    ) : PhotoSelectorEvent()
}

sealed class PhotoSelectorUiEvent : PhotoSelectorEvent() {
    object LoadAll : PhotoSelectorUiEvent()

    class ImageClicked(val imageUri: Uri) : PhotoSelectorUiEvent()
}
