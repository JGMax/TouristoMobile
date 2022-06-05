package ru.inteam.touristo.domain.post_creation.root.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationPrevScreenProvider
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationNextScreenProvider
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationStoreFactory

fun domainPostCreationModule() = module {
    factoryOf(::PostCreationPrevScreenProvider)
    factoryOf(::PostCreationNextScreenProvider)
    factoryOf(::PostCreationStoreFactory)
}
