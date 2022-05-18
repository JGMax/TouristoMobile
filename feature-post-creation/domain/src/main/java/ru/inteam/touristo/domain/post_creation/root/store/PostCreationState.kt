package ru.inteam.touristo.domain.post_creation.root.store

import ru.inteam.touristo.domain.post_creation.common.model.PhotoSelectorMedia
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationScreen
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationScreen.PHOTO_SELECTOR

data class PostCreationState(
    val isMultiSelection: Boolean = false,
    val selected: List<PhotoSelectorMedia> = emptyList(),
    val screen: PostCreationScreen = PHOTO_SELECTOR
)
