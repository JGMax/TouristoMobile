package ru.inteam.touristo.domain.store

import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.store.factory.TeaStoreFactory
import ru.inteam.touristo.data.photo_selector.PhotoSelectorRepositoryFactory
import ru.inteam.touristo.domain.store.actors.LoadImagesActor

typealias PhotoSelectorStore = Store<PhotoSelectorState, PhotoSelectorUiEvent, Nothing>

class PhotoSelectorStoreFactory internal constructor(
    private val repositoryFactory: PhotoSelectorRepositoryFactory
) : TeaStoreFactory<PhotoSelectorState, PhotoSelectorEvent, Nothing, PhotoSelectorOperation>(
    initialState = PhotoSelectorState(),
    reducer = PhotoSelectorReducer(),
    actors = {
        listOf(
            LoadImagesActor(repositoryFactory.create(this))
        )
    }
)
