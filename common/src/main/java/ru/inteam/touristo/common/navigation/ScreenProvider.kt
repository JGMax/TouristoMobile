package ru.inteam.touristo.common.navigation

interface ScreenProvider<in S> {
    fun provideScreen(screen: S): Screen
}
