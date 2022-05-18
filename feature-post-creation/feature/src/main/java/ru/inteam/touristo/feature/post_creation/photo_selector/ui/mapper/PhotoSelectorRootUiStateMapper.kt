package ru.inteam.touristo.feature.post_creation.photo_selector.ui.mapper

import ru.inteam.touristo.common.tea.UiStateMapper
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationState
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.model.PhotoSelectorRootUiState

internal class PhotoSelectorRootUiStateMapper :
    UiStateMapper<PostCreationState, PhotoSelectorRootUiState> {

    override fun map(state: PostCreationState): PhotoSelectorRootUiState {
        return PhotoSelectorRootUiState(state.isMultiSelection)
    }
}
