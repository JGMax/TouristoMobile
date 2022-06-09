package ru.inteam.touristo.presentation.post_creation.root.store

import ru.inteam.touristo.presentation.post_creation.common.model.PhotoSelectorMedia

sealed class PostCreationUiEvent {
    object OpenCurrentScreen : PostCreationUiEvent()
    object OpenNextScreen : PostCreationUiEvent()
    object ChangeSelectionStyle : PostCreationUiEvent()
    object HideChangeSelectionStyleButton : PostCreationUiEvent()
    object ShowChangeSelectionStyleButton : PostCreationUiEvent()
    object HideNextButton : PostCreationUiEvent()
    object ShowNextButton : PostCreationUiEvent()
    object OnBackPressed : PostCreationUiEvent()

    class Selected(val list: List<PhotoSelectorMedia>) : PostCreationUiEvent()
}
