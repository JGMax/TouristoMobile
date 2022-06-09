package ru.inteam.touristo.view.post_creation.root.navigation

import ru.inteam.touristo.common.navigation.FragmentLaunchParams
import ru.inteam.touristo.common.navigation.Screen
import ru.inteam.touristo.common.navigation.Screen.FragmentScreen
import ru.inteam.touristo.common.navigation.ScreenProvider
import ru.inteam.touristo.presentation.post_creation.root.navigation.PostCreationScreen
import ru.inteam.touristo.view.post_creation.location_selector.ui.LocationSelectorFragment
import ru.inteam.touristo.view.post_creation.photo_selector.ui.PhotoSelectorFragment

internal class PostCreationScreenProvider : ScreenProvider<PostCreationScreen> {

    override fun provideScreen(screen: PostCreationScreen): Screen {
        return when (screen) {
            PostCreationScreen.PHOTO_SELECTOR -> {
                val params = FragmentLaunchParams(addToBackStack = false)
                FragmentScreen(PhotoSelectorFragment.instance(), params)
            }
            PostCreationScreen.LOCATION_SELECTOR -> {
                FragmentScreen(LocationSelectorFragment.instance())
            }
        }
    }
}
