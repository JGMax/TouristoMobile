package ru.inteam.touristo.common.navigation

interface NavigationOwner<S> {
    val navigation: NavigationStore<S>
}
