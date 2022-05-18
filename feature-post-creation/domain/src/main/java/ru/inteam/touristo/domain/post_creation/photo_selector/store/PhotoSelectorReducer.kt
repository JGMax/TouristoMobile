package ru.inteam.touristo.domain.post_creation.photo_selector.store

import android.os.Build
import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.common_data.state.data
import ru.inteam.touristo.common_data.state.map
import ru.inteam.touristo.common_media.shared_media.model.media.CRField.MediaField.BUCKET_DISPLAY_NAME
import ru.inteam.touristo.common_media.shared_media.model.media.MediaResponse
import ru.inteam.touristo.common_media.shared_media.model.media.types.MediaType
import ru.inteam.touristo.common_media.shared_media.util.getFieldValue
import ru.inteam.touristo.domain.post_creation.common.model.PhotoSelectorMedia
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorAction.SelectedPhotosLimit
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorOperation.Load
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorUiEvent as UiEvent

private const val MAX_SELECTED_PHOTOS = 10

internal class PhotoSelectorReducer :
    Reducer<PhotoSelectorState, PhotoSelectorEvent, PhotoSelectorAction, PhotoSelectorOperation>() {

    private val allMediaSelector = if (Build.VERSION.SDK_INT >= 29) {
        MediaType.Images(listOf(BUCKET_DISPLAY_NAME))
    } else {
        MediaType.Images()
    }

    override fun reduce(event: PhotoSelectorEvent) {
        when (event) {
            is PhotoSelectorEvent.LoadingStatus -> {
                state {
                    val grouped = event.loadingState.map { list ->
                        list.groupBy { it.fields.getFieldValue(BUCKET_DISPLAY_NAME) }
                            .mapValues {
                                it.value.map { value ->
                                    PhotoSelectorMedia(value.id.toString(), value.contentUri)
                                }
                            }
                    }
                    copy(
                        loadingState = grouped,
                        selected = selected.ifEmpty {
                            val selectedResponse: MediaResponse? = event.loadingState.data?.first()
                            val selectedMedia = selectedResponse?.run {
                                PhotoSelectorMedia(id.toString(), contentUri)
                            }
                            listOfNotNull(selectedMedia)
                        }
                    )
                }
            }
            is UiEvent -> reduceUiEvent(event)
        }
    }

    private fun reduceUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.LoadAll -> {
                operations(Load(allMediaSelector))
            }
            is UiEvent.LoadBucket -> {
                operations(Load(allMediaSelector))
                state { copy(currentBucket = event.bucket) }
            }
            is UiEvent.ImageClicked -> state {
                val mutableSelected = selected.toMutableList()
                val media = PhotoSelectorMedia(event.imageId, event.imageUri)
                if (isMultiSelection) {
                    when {
                        media !in mutableSelected -> {
                            if (mutableSelected.size == MAX_SELECTED_PHOTOS) {
                                actions(SelectedPhotosLimit(MAX_SELECTED_PHOTOS))
                            } else {
                                mutableSelected.add(media)
                            }
                        }
                        mutableSelected.size > 1 -> {
                            mutableSelected.remove(media)
                        }
                    }
                } else {
                    mutableSelected.clear()
                    mutableSelected.add(media)
                }
                copy(selected = mutableSelected.toList())
            }
            is UiEvent.ChangeIsMultiSelection -> {
                state {
                    val currentSelected =
                        if (isMultiSelection != event.isMultiSelection) listOf(selected.last())
                        else selected
                    copy(
                        isMultiSelection = event.isMultiSelection,
                        selected = currentSelected
                    )
                }
            }
        }
    }
}
