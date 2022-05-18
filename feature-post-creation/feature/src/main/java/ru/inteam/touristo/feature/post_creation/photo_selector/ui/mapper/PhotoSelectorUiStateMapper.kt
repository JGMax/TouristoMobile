package ru.inteam.touristo.feature.post_creation.photo_selector.ui.mapper

import android.content.Context
import ru.inteam.touristo.common.tea.UiStateMapper
import ru.inteam.touristo.common_data.state.map
import ru.inteam.touristo.domain.post_creation.common.model.PhotoSelectorMedia
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorState
import ru.inteam.touristo.feature.post_creation.R
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.model.PhotoSelectorUiState
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.model.PhotoSelectorImageItem

internal class PhotoSelectorUiStateMapper(
    context: Context
) : UiStateMapper<PhotoSelectorState, PhotoSelectorUiState> {

    private val noBucketsTitle = context.getString(R.string.photo_selector_all_images_bucket)

    override fun map(state: PhotoSelectorState): PhotoSelectorUiState {
        return state.loadingState.map(
            onLoading = { mapLoadingState(it, state, state.currentBucket) },
            onSuccess = { mapSuccessState(it, state, state.currentBucket) },
            onError = { c, e -> mapErrorState(c, e, state, state.currentBucket) }
        )
    }

    private fun mapLoadingState(
        content: Map<String?, List<PhotoSelectorMedia>>?,
        state: PhotoSelectorState,
        currentBucket: String?
    ): PhotoSelectorUiState {
        if (content.isNullOrEmpty())
            return PhotoSelectorUiState(
                emptyList(),
                currentBucket ?: "",
                emptyList(),
                emptyList()
            )

        return mapSuccessState(content, state, currentBucket)
    }

    private fun mapSuccessState(
        content: Map<String?, List<PhotoSelectorMedia>>,
        state: PhotoSelectorState,
        currentBucket: String?
    ): PhotoSelectorUiState {
        val selected = state.selected
        val buckets = content.keys.filterNotNull().sortedByDescending { content[it]?.size }

        val mutableContent = content.toMutableMap()

        if (buckets.isEmpty()) {
            mutableContent[noBucketsTitle] = content.values.flatten()
        }

        val bucket = currentBucket ?: buckets.firstOrNull() ?: noBucketsTitle
        val currentGroup = mutableContent[bucket] ?: emptyList()

        return PhotoSelectorUiState(
            buckets = buckets.filter { it != currentBucket },
            content = currentGroup.map {
                PhotoSelectorImageItem(it.id, it in selected, it.uri)
            },
            currentBucket = bucket,
            selected = selected
        )
    }

    private fun mapErrorState(
        content: Map<String?, List<PhotoSelectorMedia>>?,
        e: Throwable,
        state: PhotoSelectorState,
        currentBucket: String?
    ): PhotoSelectorUiState {
        if (content.isNullOrEmpty())
            return PhotoSelectorUiState(
                emptyList(),
                currentBucket ?: "",
                emptyList(),
                emptyList()
            )

        return mapSuccessState(content, state, currentBucket)
    }
}
