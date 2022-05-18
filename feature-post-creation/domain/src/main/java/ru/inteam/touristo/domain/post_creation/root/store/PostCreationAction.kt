package ru.inteam.touristo.domain.post_creation.root.store

import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationScreen

sealed class PostCreationAction {
    class OpenScreen(val screen: PostCreationScreen) : PostCreationAction()
}
