package ru.inteam.touristo.domain.store

import android.os.Build
import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.common_data.state.data
import ru.inteam.touristo.common_data.state.map
import ru.inteam.touristo.common_media.shared_media.model.media.CRField.MediaField.BUCKET_DISPLAY_NAME
import ru.inteam.touristo.common_media.shared_media.model.media.types.MediaType
import ru.inteam.touristo.common_media.shared_media.util.getFieldValue
import ru.inteam.touristo.domain.store.model.PhotoSelectorMedia

internal class PhotoSelectorReducer :
    Reducer<PhotoSelectorState, PhotoSelectorEvent, Nothing, PhotoSelectorOperation>() {

    private val allMediaSelector = if (Build.VERSION.SDK_INT >= 29) {
        MediaType.Images(listOf(BUCKET_DISPLAY_NAME))
    } else {
        MediaType.Images()
    }

    override fun reduce(event: PhotoSelectorEvent) {
        when (event) {
            is PhotoSelectorEvent.LoadingStatus -> state {
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
                    selected = selected.ifEmpty { listOfNotNull(event.loadingState.data?.first()?.contentUri) }
                )
            }
            is PhotoSelectorUiEvent -> reduceUiEvent(event)
        }
    }

    private fun reduceUiEvent(event: PhotoSelectorUiEvent) {
        when (event) {
            is PhotoSelectorUiEvent.LoadAll -> {
                operations(PhotoSelectorOperation.Load(allMediaSelector))
            }
            is PhotoSelectorUiEvent.ImageClicked -> {
                state {
                    val mutableSelected = selected.toMutableList()
                    if (event.imageUri !in mutableSelected) {
                        mutableSelected.add(event.imageUri)
                    } else if (mutableSelected.size > 1) {
                        mutableSelected.remove(event.imageUri)
                    }
                    copy(selected = mutableSelected)
                }
            }
        }
    }
}