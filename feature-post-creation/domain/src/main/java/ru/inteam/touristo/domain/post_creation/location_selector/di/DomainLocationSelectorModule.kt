package ru.inteam.touristo.domain.post_creation.location_selector.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorStoreFactory

fun domainLocationSelectorModule() = module {
    factoryOf(::LocationSelectorStoreFactory)
}
