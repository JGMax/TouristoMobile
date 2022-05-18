package ru.inteam.touristo.common.navigation

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit

fun <S> FragmentActivity.NavigationStore(
    containerId: Int,
    screenProvider: () -> ScreenProvider<S>
): Lazy<NavigationStore<S>> {
    val navigationController = object : NavigationController<S> {
        private val container = containerId
        override val screenProvider: ScreenProvider<S> = screenProvider()

        override fun openScreen(fragment: Fragment, screen: S) {
            // TODO navigation params
            supportFragmentManager.commit {
                replace(container, fragment, screen.toString())
                addToBackStack(screen.toString())
            }
        }

        override fun openScreen(intent: Intent) {
            startActivity(intent)
        }

        override fun back() {
            onBackPressed()
        }
    }
    return NavigationLazy { navigationController }
}

fun <S> Fragment.NavigationStore(
    navigationController: () -> NavigationController<S> = { context as NavigationController<S> }
): Lazy<NavigationStore<S>> {
    return NavigationLazy(navigationController)
}
