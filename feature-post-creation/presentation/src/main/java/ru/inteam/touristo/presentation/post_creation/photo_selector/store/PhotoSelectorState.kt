package ru.inteam.touristo.presentation.post_creation.photo_selector.store

import ru.inteam.touristo.common_data.state.LoadingState
import ru.inteam.touristo.presentation.post_creation.common.model.PhotoSelectorMedia

data class PhotoSelectorState(
    val loadingState: LoadingState<List<PhotoSelectorMedia>> = LoadingState.Loading.Default(),
    val buckets: LoadingState<List<String?>> = LoadingState.Loading.Default(),
    val selected: List<PhotoSelectorMedia> = emptyList(),
    val isMultiselect: Boolean = false,
    val currentBucket: String? = null
)
