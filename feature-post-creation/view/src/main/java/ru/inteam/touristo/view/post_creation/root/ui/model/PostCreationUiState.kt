package ru.inteam.touristo.view.post_creation.root.ui.model

import androidx.annotation.ColorInt
import ru.inteam.touristo.carousel.model.CarouselItem
import ru.inteam.touristo.presentation.post_creation.root.navigation.PostCreationScreen

internal data class PostCreationUiState(
    val selected: List<CarouselItem>,
    @ColorInt
    val selectionButtonTint: Int,
    val screen: PostCreationScreen,
    val isSelectionButtonVisible: Boolean,
    val isAcceptButtonVisible: Boolean
)
