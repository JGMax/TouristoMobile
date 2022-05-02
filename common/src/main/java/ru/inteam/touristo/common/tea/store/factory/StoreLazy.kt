package ru.inteam.touristo.common.tea.store.factory

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import ru.inteam.touristo.common.tea.Store
import kotlin.reflect.KClass

private const val DEFAULT_KEY = "TEA_STORE_DEFAULT_KEY"

class StoreLazy<S : Store<*, *, *>>(
    private val viewModelClass: KClass<S>,
    private val key: String?,
    private val storeProducer: () -> ViewModelStore,
    private val factoryProducer: () -> ViewModelProvider.Factory
) : Lazy<S> {
    private var cached: S? = null

    override val value: S
        get() {
            val cachedStore = cached
            return if (cachedStore == null) {
                val factory = factoryProducer()
                val store = storeProducer()
                val mKey = "${key ?: DEFAULT_KEY}#${viewModelClass.java.canonicalName}"
                ViewModelProvider(store, factory)[mKey, viewModelClass.java].also {
                    cached = it
                }
            } else {
                cachedStore
            }
        }

    override fun isInitialized(): Boolean = cached != null
}
