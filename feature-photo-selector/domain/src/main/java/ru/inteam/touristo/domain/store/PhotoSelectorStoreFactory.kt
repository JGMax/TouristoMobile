package ru.inteam.touristo.domain.store

import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.store.factory.TeaStoreFactory
import ru.inteam.touristo.data.photo_selector.PhotoSelectorRepositoryFactory
import ru.inteam.touristo.domain.store.actors.LoadImagesActor
import ru.inteam.touristo.domain.store.PhotoSelectorAction as Action
import ru.inteam.touristo.domain.store.PhotoSelectorEvent as Event
import ru.inteam.touristo.domain.store.PhotoSelectorOperation as Operation
import ru.inteam.touristo.domain.store.PhotoSelectorState as State
import ru.inteam.touristo.domain.store.PhotoSelectorUiEvent as UiEvent

typealias PhotoSelectorStore = Store<State, UiEvent, Nothing>

class PhotoSelectorStoreFactory internal constructor(
    private val repositoryFactory: PhotoSelectorRepositoryFactory
) : TeaStoreFactory<State, Event, Action, Operation>(
    initialState = State(),
    reducer = PhotoSelectorReducer(),
    actors = {
        listOf(
            LoadImagesActor(repositoryFactory.create(this))
        )
    }
)
