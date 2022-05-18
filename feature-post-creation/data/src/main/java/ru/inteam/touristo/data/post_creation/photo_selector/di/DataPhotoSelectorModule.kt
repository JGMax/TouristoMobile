package ru.inteam.touristo.data.post_creation.photo_selector.di

import org.koin.dsl.module
import ru.inteam.touristo.data.post_creation.photo_selector.PhotoSelectorRepositoryFactory

fun dataPhotoSelectorModule() = module {
    factory { PhotoSelectorRepositoryFactory(get()) }
}
