package ru.inteam.touristo.presentation.post_creation.root.navigation

internal class PostCreationPrevScreenProvider {
    fun provide(screen: PostCreationScreen): PostCreationScreen? {
        return when(screen) {
            PostCreationScreen.LOCATION_SELECTOR -> PostCreationScreen.PHOTO_SELECTOR
            PostCreationScreen.PHOTO_SELECTOR -> null
        }
    }
}
