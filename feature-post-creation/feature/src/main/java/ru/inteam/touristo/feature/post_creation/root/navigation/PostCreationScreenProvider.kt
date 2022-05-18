package ru.inteam.touristo.feature.post_creation.root.navigation

import ru.inteam.touristo.common.navigation.Screen
import ru.inteam.touristo.common.navigation.Screen.FragmentScreen
import ru.inteam.touristo.common.navigation.ScreenProvider
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationScreen
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.PhotoSelectorFragment

internal class PostCreationScreenProvider : ScreenProvider<PostCreationScreen> {

    override fun provideScreen(screen: PostCreationScreen): Screen {
        return when(screen) {
            PostCreationScreen.PHOTO_SELECTOR -> FragmentScreen(PhotoSelectorFragment.instance())
        }
    }
}
