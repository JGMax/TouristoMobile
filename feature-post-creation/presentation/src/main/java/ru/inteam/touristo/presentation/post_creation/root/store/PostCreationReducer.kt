package ru.inteam.touristo.presentation.post_creation.root.store

import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.presentation.post_creation.root.navigation.PostCreationNextScreenProvider
import ru.inteam.touristo.presentation.post_creation.root.navigation.PostCreationPrevScreenProvider
import ru.inteam.touristo.presentation.post_creation.root.store.PostCreationAction.OpenScreen

internal class PostCreationReducer(
    private val nextScreenProvider: PostCreationNextScreenProvider,
    private val prevScreenProvider: PostCreationPrevScreenProvider
) : Reducer<PostCreationState, PostCreationUiEvent, PostCreationAction, Nothing>() {

    override fun reduce(event: PostCreationUiEvent) {
        when (event) {
            is PostCreationUiEvent.OpenCurrentScreen -> actions(OpenScreen(state.screen))
            is PostCreationUiEvent.Selected -> state { copy(selected = event.list) }
            is PostCreationUiEvent.OpenNextScreen -> {
                val nextScreen = nextScreenProvider.provide(state.screen)
                if (nextScreen != null) {
                    actions(OpenScreen(nextScreen))
                    state { copy(screen = nextScreen) }
                }
            }
            is PostCreationUiEvent.ChangeSelectionStyle -> state {
                copy(isMultiSelection = !isMultiSelection)
            }
            is PostCreationUiEvent.OnBackPressed -> state {
                prevScreenProvider.provide(screen)?.let { copy(screen = it) } ?: this
            }
            PostCreationUiEvent.HideChangeSelectionStyleButton -> state {
                copy(isMultiSelectionAvailable = false)
            }
            PostCreationUiEvent.HideNextButton -> state {
                copy(isNextScreenAvailable = false)
            }
            PostCreationUiEvent.ShowChangeSelectionStyleButton -> state {
                copy(isMultiSelectionAvailable = true)
            }
            PostCreationUiEvent.ShowNextButton -> state {
                copy(isNextScreenAvailable = true)
            }
        }
    }
}
