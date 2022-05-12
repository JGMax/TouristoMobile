package ru.inteam.touristo.feature.photo_selector.ui.model

import androidx.annotation.ColorInt
import ru.inteam.touristo.carousel.model.CarouselItem
import ru.inteam.touristo.recycler.item.RecyclerItem

internal data class PhotoSelectorUiState(
    val buckets: List<String>,
    val currentBucket: String,
    val selected: List<CarouselItem>,
    val content: List<RecyclerItem>,
    @ColorInt
    val selectionButtonTint: Int
)
