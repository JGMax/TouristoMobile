package ru.inteam.touristo.domain.di

import org.koin.dsl.module
import ru.inteam.touristo.domain.store.PhotoSelectorStoreFactory


fun domainPhotoSelectorModule() = module {
    factory { PhotoSelectorStoreFactory(get()) }
}
