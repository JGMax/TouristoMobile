package ru.inteam.touristo.domain.post_creation.location_selector.store.actor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.inteam.touristo.common.tea.Actor
import ru.inteam.touristo.common_data.DataStorage
import ru.inteam.touristo.data.post_creation.location_selector.LocationSelectorRepositoryFactory.Companion.LAT_ARG
import ru.inteam.touristo.data.post_creation.location_selector.LocationSelectorRepositoryFactory.Companion.LNG_ARG
import ru.inteam.touristo.data.post_creation.location_selector.api.model.LocationSelectorLocationModel
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorEvent
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorEvent.LoadingStatus
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorOperation
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorOperation.Load

internal class LocationSelectorLoadActor(
    private val dataStorage: DataStorage<List<LocationSelectorLocationModel>>
) : Actor<LocationSelectorOperation, LocationSelectorEvent> {

    override fun process(operations: Flow<LocationSelectorOperation>): Flow<LocationSelectorEvent> {
        return operations.filterIsInstance<Load>()
            .flatMapLatest {

                dataStorage.data(LAT_ARG to it.lat, LNG_ARG to it.lng)
            }
            .map { LoadingStatus(it) }
    }
}
