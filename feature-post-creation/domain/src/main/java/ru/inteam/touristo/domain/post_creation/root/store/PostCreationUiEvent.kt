package ru.inteam.touristo.domain.post_creation.root.store

import ru.inteam.touristo.domain.post_creation.common.model.PhotoSelectorMedia

sealed class PostCreationUiEvent {
    object OpenCurrentScreen : PostCreationUiEvent()
    object OpenNextScreen : PostCreationUiEvent()
    object ChangeSelectionStyle : PostCreationUiEvent()

    class Selected(val list: List<PhotoSelectorMedia>) : PostCreationUiEvent()
}
