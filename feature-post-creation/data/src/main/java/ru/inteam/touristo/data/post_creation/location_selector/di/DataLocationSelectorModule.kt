package ru.inteam.touristo.data.post_creation.location_selector.di

import org.koin.dsl.module
import ru.inteam.touristo.data.post_creation.location_selector.LocationSelectorRepositoryFactory
import ru.inteam.touristo.data.post_creation.location_selector.api.LocationsSelectorApiImpl

fun dataLocationSelectorModule() = module {
    factory { LocationSelectorRepositoryFactory(LocationsSelectorApiImpl()) }
}
