package ru.inteam.touristo.common.navigation

class NavigationLazy<S> internal constructor(
    private val navigationController: () -> NavigationController<S>,
) : Lazy<NavigationStore<S>> {
    private var cached: NavigationStore<S>? = null

    override val value: NavigationStore<S>
        get() {
            if (cached == null) {
                cached = NavigationStore(navigationController())
            }
            return cached!!
        }

    override fun isInitialized(): Boolean = cached != null
}
