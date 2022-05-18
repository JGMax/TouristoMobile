package ru.inteam.touristo.feature.post_creation.photo_selector.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.mapper.PhotoSelectorRootUiStateMapper
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.mapper.PhotoSelectorUiStateMapper

fun featurePhotoSelectorModule() = module {
    factoryOf(::PhotoSelectorUiStateMapper)
    factoryOf(::PhotoSelectorRootUiStateMapper)
}
