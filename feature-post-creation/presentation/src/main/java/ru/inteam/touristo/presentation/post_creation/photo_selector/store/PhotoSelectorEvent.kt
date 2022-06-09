package ru.inteam.touristo.presentation.post_creation.photo_selector.store

import android.net.Uri
import ru.inteam.touristo.common_data.state.LoadingState
import ru.inteam.touristo.common_media.shared_media.model.media.MediaResponse

sealed class PhotoSelectorEvent {
    internal class LoadingStatus(
        val loadingState: LoadingState<List<MediaResponse>>
    ) : PhotoSelectorEvent()

    internal class LoadingStatusBuckets(
        val loadingState: LoadingState<List<String?>>
    ) : PhotoSelectorEvent()
}

sealed class PhotoSelectorUiEvent : PhotoSelectorEvent() {
    object LoadAll : PhotoSelectorUiEvent()
    class LoadBucket(val bucket: String?) : PhotoSelectorUiEvent()

    class ChangeIsMultiSelection(val isMultiselect: Boolean) : PhotoSelectorUiEvent()
    class ImageClicked(val imageId: String, val imageUri: Uri) : PhotoSelectorUiEvent()
}
