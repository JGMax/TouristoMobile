package ru.inteam.touristo.domain.post_creation.location_selector.store

import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.store.factory.TeaStoreFactory
import ru.inteam.touristo.common_data.DataStorage
import ru.inteam.touristo.data.post_creation.location_selector.LocationSelectorRepositoryFactory
import ru.inteam.touristo.data.post_creation.location_selector.api.model.LocationSelectorLocationModel
import ru.inteam.touristo.domain.post_creation.location_selector.store.actor.LocationSelectorLoadActor
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorEvent as Event
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorOperation as Operation
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorState as State
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorUiEvent as UiEvent

typealias LocationSelectorStore = Store<State, UiEvent, Nothing>

class LocationSelectorStoreFactory internal constructor(
    reposFactory: LocationSelectorRepositoryFactory
) : TeaStoreFactory<State, Event, Nothing, Operation>(
    initialState = State(),
    reducer = LocationSelectorReducer(),
    actors = { listOf(LocationSelectorLoadActor(reposFactory.create(this))) }
)
