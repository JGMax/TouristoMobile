package ru.inteam.touristo.domain.post_creation.photo_selector.store

import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.store.factory.TeaStoreFactory
import ru.inteam.touristo.data.post_creation.photo_selector.PhotoSelectorRepositoryFactory
import ru.inteam.touristo.domain.post_creation.photo_selector.store.actors.LoadBucketsActor
import ru.inteam.touristo.domain.post_creation.photo_selector.store.actors.LoadImagesActor
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorAction as Action
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorEvent as Event
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorOperation as Operation
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorState as State
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorUiEvent as UiEvent

typealias PhotoSelectorStore = Store<State, UiEvent, Nothing>

class PhotoSelectorStoreFactory internal constructor(
    private val repositoryFactory: PhotoSelectorRepositoryFactory
) : TeaStoreFactory<State, Event, Action, Operation>(
    initialState = State(),
    reducer = PhotoSelectorReducer(),
    actors = {
        listOf(
            LoadBucketsActor(repositoryFactory.create(this)),
            LoadImagesActor(repositoryFactory.create(this))
        )
    }
)
