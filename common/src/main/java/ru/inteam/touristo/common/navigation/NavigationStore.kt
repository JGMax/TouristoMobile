package ru.inteam.touristo.common.navigation

import ru.inteam.touristo.common.navigation.Screen.ActivityScreen
import ru.inteam.touristo.common.navigation.Screen.FragmentScreen

class NavigationStore<S> internal constructor(
    private val controller: NavigationController<S>
) {
    fun openScreen(screen: S) {
        when(val data = controller.screenProvider.provideScreen(screen)) {
            is FragmentScreen -> controller.openScreen(data.fragment, screen)
            is ActivityScreen -> controller.openScreen(data.intent)
        }
    }

    fun back() {
        controller.back()
    }
}
