package ru.inteam.touristo.domain.post_creation.root.navigation

import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationScreen.LOCATION_SELECTOR
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationScreen.PHOTO_SELECTOR

internal class PostCreationNextScreenProvider {
    fun provide(screen: PostCreationScreen): PostCreationScreen? {
        return when(screen) {
            PHOTO_SELECTOR -> LOCATION_SELECTOR
            LOCATION_SELECTOR -> null
        }
    }
}
