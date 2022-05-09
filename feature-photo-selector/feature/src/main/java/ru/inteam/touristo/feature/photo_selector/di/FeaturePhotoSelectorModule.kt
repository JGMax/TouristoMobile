package ru.inteam.touristo.feature.photo_selector.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.inteam.touristo.domain.store.PhotoSelectorStoreFactory
import ru.inteam.touristo.feature.photo_selector.ui.mapper.PhotoSelectorUiStateMapper

fun featurePhotoSelectorModule() = module {
    factory { PhotoSelectorUiStateMapper(androidContext().resources) }
}
