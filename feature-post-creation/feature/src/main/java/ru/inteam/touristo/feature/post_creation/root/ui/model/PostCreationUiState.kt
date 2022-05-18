package ru.inteam.touristo.feature.post_creation.root.ui.model

import androidx.annotation.ColorInt
import ru.inteam.touristo.carousel.model.CarouselItem
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationScreen

internal data class PostCreationUiState(
    val selected: List<CarouselItem>,
    @ColorInt
    val selectionButtonTint: Int,
    val screen: PostCreationScreen
)
