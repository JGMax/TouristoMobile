package ru.inteam.touristo.common.navigation

import android.content.Intent
import androidx.fragment.app.Fragment

sealed interface Screen {
    class FragmentScreen(
        val fragment: Fragment,
        val params: FragmentLaunchParams = FragmentLaunchParams()
    ) : Screen

    class ActivityScreen(
        val intent: Intent,
        val params: ActivityLaunchParams = ActivityLaunchParams()
    ) : Screen
}
