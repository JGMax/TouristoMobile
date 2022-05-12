package ru.inteam.touristo.feature.photo_selector.ui.mapper

import android.content.Context
import android.graphics.Color
import ru.inteam.touristo.carousel.model.CarouselItem
import ru.inteam.touristo.common.tea.UiStateMapper
import ru.inteam.touristo.common_data.state.map
import ru.inteam.touristo.common_ui.R.color
import ru.inteam.touristo.domain.store.PhotoSelectorState
import ru.inteam.touristo.domain.store.model.PhotoSelectorMedia
import ru.inteam.touristo.feature.photo_selector.R
import ru.inteam.touristo.feature.photo_selector.ui.model.PhotoSelectorUiState
import ru.inteam.touristo.feature.photo_selector.ui.recycler.model.PhotoSelectorImageItem

internal class PhotoSelectorUiStateMapper(
    context: Context
) : UiStateMapper<PhotoSelectorState, PhotoSelectorUiState> {

    private val allImagesBucket =
        listOf(context.getString(R.string.photo_selector_all_images_bucket))
    private val multiSelectionEnabledButtonTint = context.getColor(color.main_100)
    private val multiSelectionDisabledButtonTint = context.getColor(color.base_0)

    override fun map(state: PhotoSelectorState): PhotoSelectorUiState {
        val currentBucket = state.currentBucket ?: allImagesBucket.first()
        return state.loadingState.map(
            onLoading = { mapLoadingState(it, state, currentBucket) },
            onSuccess = { mapSuccessState(it, state, currentBucket) },
            onError = { c, e -> mapErrorState(c, e, state, currentBucket) }
        )
    }

    private fun mapLoadingState(
        content: Map<String?, List<PhotoSelectorMedia>>?,
        state: PhotoSelectorState,
        currentBucket: String
    ): PhotoSelectorUiState {
        if (content.isNullOrEmpty())
            return PhotoSelectorUiState(
                allImagesBucket,
                currentBucket,
                emptyList(),
                emptyList(),
                Color.TRANSPARENT
            )

        return mapSuccessState(content, state, currentBucket)
    }

    private fun mapSuccessState(
        content: Map<String?, List<PhotoSelectorMedia>>,
        state: PhotoSelectorState,
        currentBucket: String
    ): PhotoSelectorUiState {
        val selected = state.selected
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
            selected = selected.map { CarouselItem(it) },
            selectionButtonTint = multiSelectionEnabledButtonTint.takeIf {
                state.isMultiSelection
            } ?: multiSelectionDisabledButtonTint
        )
    }

    private fun mapErrorState(
        content: Map<String?, List<PhotoSelectorMedia>>?,
        e: Throwable,
        state: PhotoSelectorState,
        currentBucket: String
    ): PhotoSelectorUiState {
        if (content.isNullOrEmpty())
            return PhotoSelectorUiState(
                allImagesBucket,
                currentBucket,
                emptyList(),
                emptyList(),
                Color.TRANSPARENT
            )

        return mapSuccessState(content, state, currentBucket)
    }
}
