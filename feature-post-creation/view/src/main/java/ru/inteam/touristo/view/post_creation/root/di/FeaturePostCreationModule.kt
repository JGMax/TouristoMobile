package ru.inteam.touristo.view.post_creation.root.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.inteam.touristo.common.navigation.ScreenProvider
import ru.inteam.touristo.view.post_creation.root.navigation.PostCreationScreenProvider

fun featurePostCreationModule() = module {
    factoryOf(::PostCreationScreenProvider) bind ScreenProvider::class
}
