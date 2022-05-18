package ru.inteam.touristo.common.navigation

import android.content.Intent
import androidx.fragment.app.Fragment

interface NavigationController<S> {
    val screenProvider: ScreenProvider<S>
    fun openScreen(fragment: Fragment, screen: S)
    fun openScreen(intent: Intent)
    fun back()
}
