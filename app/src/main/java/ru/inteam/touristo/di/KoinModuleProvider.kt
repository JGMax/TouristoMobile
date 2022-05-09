package ru.inteam.touristo.di

import org.koin.core.module.Module
import ru.inteam.touristo.data.photo_selector.di.dataPhotoSelectorModule
import ru.inteam.touristo.di.application.applicationModule
import ru.inteam.touristo.domain.di.domainPhotoSelectorModule
import ru.inteam.touristo.feature.photo_selector.di.featurePhotoSelectorModule


object KoinModuleProvider {
    @JvmStatic
    fun modules(): List<Module> = listOf(
        applicationModule,

        dataPhotoSelectorModule(),
        domainPhotoSelectorModule(),
        featurePhotoSelectorModule()
    )
}
