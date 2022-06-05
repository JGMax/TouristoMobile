package ru.inteam.touristo.di

import org.koin.core.module.Module
import ru.inteam.touristo.data.post_creation.location_selector.di.dataLocationSelectorModule
import ru.inteam.touristo.data.post_creation.photo_selector.di.dataPhotoSelectorModule
import ru.inteam.touristo.di.application.applicationModule
import ru.inteam.touristo.domain.post_creation.location_selector.di.domainLocationSelectorModule
import ru.inteam.touristo.domain.post_creation.photo_selector.di.domainPhotoSelectorModule
import ru.inteam.touristo.domain.post_creation.root.di.domainPostCreationModule
import ru.inteam.touristo.feature.post_creation.root.di.featurePostCreationModule


object KoinModuleProvider {
    @JvmStatic
    fun modules(): List<Module> = listOf(
        applicationModule,

        dataPhotoSelectorModule(),
        dataLocationSelectorModule(),

        domainPhotoSelectorModule(),
        domainPostCreationModule(),
        domainLocationSelectorModule(),

        featurePostCreationModule()
    )
}
