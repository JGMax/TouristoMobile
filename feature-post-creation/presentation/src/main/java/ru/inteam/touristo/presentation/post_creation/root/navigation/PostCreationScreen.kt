package ru.inteam.touristo.presentation.post_creation.root.navigation

import ru.inteam.touristo.common.navigation.NavigationStore

typealias PostCreationNavigation = NavigationStore<PostCreationScreen>

enum class PostCreationScreen {
    PHOTO_SELECTOR, LOCATION_SELECTOR
}
