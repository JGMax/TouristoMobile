package ru.inteam.touristo.domain.post_creation.root.store

import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationNextScreenProvider
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationAction.OpenScreen

internal class PostCreationReducer(
    private val nextScreenProvider: PostCreationNextScreenProvider
) : Reducer<PostCreationState, PostCreationUiEvent, PostCreationAction, Nothing>() {

    override fun reduce(event: PostCreationUiEvent) {
        when (event) {
            is PostCreationUiEvent.OpenCurrentScreen -> actions(OpenScreen(state.screen))
            is PostCreationUiEvent.Selected -> state { copy(selected = event.list) }
            is PostCreationUiEvent.OpenNextScreen -> {
                val nextScreen = nextScreenProvider.provide(state.screen)
                actions(OpenScreen(nextScreen))
                state { copy(screen = nextScreen) }
            }
            is PostCreationUiEvent.ChangeSelectionStyle -> state {
                copy(isMultiSelection = !isMultiSelection)
            }
        }
    }
}
