package ru.inteam.touristo.presentation.post_creation.photo_selector.di

import org.koin.dsl.module
import ru.inteam.touristo.presentation.post_creation.photo_selector.store.PhotoSelectorStoreFactory


fun domainPhotoSelectorModule() = module {
    factory { PhotoSelectorStoreFactory(get()) }
}
