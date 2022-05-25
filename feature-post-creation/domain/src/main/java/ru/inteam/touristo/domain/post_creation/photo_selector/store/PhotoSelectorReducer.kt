package ru.inteam.touristo.domain.post_creation.photo_selector.store

import android.os.Build
import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.common_data.state.LoadingState
import ru.inteam.touristo.common_data.state.data
import ru.inteam.touristo.common_data.state.map
import ru.inteam.touristo.common_data.state.mapList
import ru.inteam.touristo.common_media.shared_media.model.media.CRField.MediaField.BUCKET_DISPLAY_NAME
import ru.inteam.touristo.common_media.shared_media.model.media.MediaSelector
import ru.inteam.touristo.common_media.shared_media.model.media.Selector
import ru.inteam.touristo.common_media.shared_media.model.media.types.MediaType
import ru.inteam.touristo.domain.post_creation.common.model.PhotoSelectorMedia
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorAction.SelectedPhotosLimit
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorOperation.Load
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorOperation.LoadBuckets
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorUiEvent as UiEvent

private const val MAX_SELECTED_PHOTOS = 10

internal class PhotoSelectorReducer :
    Reducer<PhotoSelectorState, PhotoSelectorEvent, PhotoSelectorAction, PhotoSelectorOperation>() {

    private fun buildMediaSelector(bucket: String? = null): MediaSelector {
        return if (Build.VERSION.SDK_INT >= 29) {
            MediaType.Images(
                requiredFields = listOf(BUCKET_DISPLAY_NAME),
                selector = Selector.createEqual(BUCKET_DISPLAY_NAME, bucket)
            )
        } else {
            MediaType.Images()
        }
    }

    private fun buildBucketsSelector(): MediaSelector? {
        return if (Build.VERSION.SDK_INT >= 29) {
            MediaType.Images(requiredFields = listOf(BUCKET_DISPLAY_NAME))
        } else {
            null
        }
    }

    override fun reduce(event: PhotoSelectorEvent) {
        when (event) {
            is PhotoSelectorEvent.LoadingStatusBuckets -> {
                state { copy(buckets = event.loadingState) }
                if (event.loadingState is LoadingState.Loaded) {
                    val currentBucket = event.loadingState.content.firstOrNull()
                    state { copy(currentBucket = currentBucket) }
                    operations(Load(buildMediaSelector(currentBucket)))
                }
            }
            is PhotoSelectorEvent.LoadingStatus -> {
                state {
                    val media = event.loadingState.mapList {
                        PhotoSelectorMedia(it.id.toString(), it.contentUri)
                    }.map {
                        if (loadingState is LoadingState.Loaded) {
                            loadingState.content + it
                        } else {
                            it
                        }
                    }
                    copy(
                        loadingState = media,
                        selected = selected.ifEmpty { listOfNotNull(media.data?.firstOrNull()) }
                    )
                }
            }
            is UiEvent -> reduceUiEvent(event)
        }
    }

    private fun reduceUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.LoadAll -> {
                val selector = buildBucketsSelector()
                if (selector == null) {
                    state { copy(buckets = LoadingState.Loaded(listOf(null))) }
                    operations(Load(buildMediaSelector(null)))
                } else {
                    operations(LoadBuckets(selector))
                }
            }
            is UiEvent.LoadBucket -> {
                event.bucket?.let { state { copy(currentBucket = event.bucket) } }
                operations(Load(buildMediaSelector(state.currentBucket)))
            }
            is UiEvent.ImageClicked -> state {
                val mutableSelected = selected.toMutableList()
                val media = PhotoSelectorMedia(event.imageId, event.imageUri)
                if (isMultiselect) {
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
                        if (isMultiselect != event.isMultiselect) listOf(selected.last())
                        else selected
                    copy(
                        isMultiselect = event.isMultiselect,
                        selected = currentSelected
                    )
                }
            }
        }
    }
}
