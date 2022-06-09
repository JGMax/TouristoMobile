package ru.inteam.touristo.presentation.post_creation.root.store

import ru.inteam.touristo.presentation.post_creation.root.navigation.PostCreationScreen

sealed class PostCreationAction {
    class OpenScreen(val screen: PostCreationScreen) : PostCreationAction()
}
