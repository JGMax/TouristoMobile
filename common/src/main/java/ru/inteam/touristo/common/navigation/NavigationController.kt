package ru.inteam.touristo.common.navigation

import android.content.Intent
import androidx.fragment.app.Fragment

interface NavigationController<S> {
    val screenProvider: ScreenProvider<S>
    fun openScreen(fragment: Fragment, params: FragmentLaunchParams)
    fun openScreen(intent: Intent, params: ActivityLaunchParams)
    fun back()
}
