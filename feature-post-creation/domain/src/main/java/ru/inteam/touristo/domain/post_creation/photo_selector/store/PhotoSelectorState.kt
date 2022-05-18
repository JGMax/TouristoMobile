package ru.inteam.touristo.domain.post_creation.photo_selector.store

import ru.inteam.touristo.common_data.state.LoadingState
import ru.inteam.touristo.domain.post_creation.common.model.PhotoSelectorMedia

data class PhotoSelectorState(
    val loadingState: LoadingState<Map<String?, List<PhotoSelectorMedia>>> = LoadingState.Loading.Default(),
    val selected: List<PhotoSelectorMedia> = emptyList(),
    val isMultiSelection: Boolean = false,
    val currentBucket: String? = null
)
