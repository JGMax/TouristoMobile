package ru.inteam.touristo.view.post_creation.photo_selector.ui.model

import ru.inteam.touristo.presentation.post_creation.common.model.PhotoSelectorMedia
import ru.inteam.touristo.recycler.item.RecyclerItem

internal data class PhotoSelectorUiState(
    val buckets: List<String>,
    val currentBucket: String,
    val selected: List<PhotoSelectorMedia>,
    val content: List<RecyclerItem>
) {
    val needShowChevron = buckets.isNotEmpty()
}
