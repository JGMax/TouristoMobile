package ru.inteam.touristo.feature.photo_selector.ui.mapper

import android.content.res.Resources
import android.net.Uri
import ru.inteam.touristo.carousel.model.CarouselItem
import ru.inteam.touristo.common.tea.UiStateMapper
import ru.inteam.touristo.common_data.state.map
import ru.inteam.touristo.feature.photo_selector.R
import ru.inteam.touristo.domain.store.PhotoSelectorState
import ru.inteam.touristo.domain.store.model.PhotoSelectorMedia
import ru.inteam.touristo.feature.photo_selector.ui.model.PhotoSelectorUiState
import ru.inteam.touristo.feature.photo_selector.ui.recycler.model.PhotoSelectorImageItem

internal class PhotoSelectorUiStateMapper(
    resources: Resources
) : UiStateMapper<PhotoSelectorState, PhotoSelectorUiState> {

    private val allImagesBucket =
        listOf(resources.getString(R.string.photo_selector_all_images_bucket))

    override fun map(state: PhotoSelectorState): PhotoSelectorUiState {
        val currentBucket = state.currentBucket ?: allImagesBucket.first()
        return state.loadingState.map(
            onLoading = { mapLoadingState(it, state.selected, currentBucket) },
            onSuccess = { mapSuccessState(it, state.selected, currentBucket) },
            onError = { c, e -> mapErrorState(c, e, state.selected, currentBucket) }
        )
    }

    private fun mapLoadingState(
        content: Map<String?, List<PhotoSelectorMedia>>?,
        selected: List<Uri>,
        currentBucket: String
    ): PhotoSelectorUiState {
        if (content.isNullOrEmpty())
            return PhotoSelectorUiState(allImagesBucket, currentBucket, emptyList(), emptyList())

        return mapSuccessState(content, selected, currentBucket)
    }

    private fun mapSuccessState(
        content: Map<String?, List<PhotoSelectorMedia>>,
        selected: List<Uri>,
        currentBucket: String
    ): PhotoSelectorUiState {

        val grouped = content.toMutableMap()
        grouped[allImagesBucket.first()] = content.values.flatten()
        val buckets = grouped.keys.filterNotNull().sortedByDescending { grouped[it]?.size }
        val currentGroup = grouped[currentBucket] ?: emptyList()

        return PhotoSelectorUiState(
            buckets = buckets.filter { it != currentBucket },
            content = currentGroup.map {
                PhotoSelectorImageItem(it.id, it.uri in selected, it.uri)
            },
            currentBucket = currentBucket,
            selected = selected.map { CarouselItem(it) }
        )
    }

    private fun mapErrorState(
        content: Map<String?, List<PhotoSelectorMedia>>?,
        e: Throwable,
        selected: List<Uri>,
        currentBucket: String
    ): PhotoSelectorUiState {
        if (content.isNullOrEmpty())
            return PhotoSelectorUiState(allImagesBucket, currentBucket, emptyList(), emptyList())

        return mapSuccessState(content, selected, currentBucket)
    }
}
