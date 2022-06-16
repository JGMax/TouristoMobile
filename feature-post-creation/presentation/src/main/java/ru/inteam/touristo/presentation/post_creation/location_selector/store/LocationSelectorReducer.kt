package ru.inteam.touristo.presentation.post_creation.location_selector.store

import ru.inteam.touristo.common.tea.Reducer
import ru.inteam.touristo.common_data.state.data
import ru.inteam.touristo.common_data.state.mapList
import ru.inteam.touristo.presentation.post_creation.location_selector.model.LocationModel
import ru.inteam.touristo.presentation.post_creation.location_selector.store.LocationSelectorOperation.Load
import ru.inteam.touristo.presentation.post_creation.location_selector.store.LocationSelectorUiEvent.LoadLocations
import ru.inteam.touristo.presentation.post_creation.location_selector.store.LocationSelectorUiEvent.LocationClicked
import java.math.BigDecimal

internal class LocationSelectorReducer :
    Reducer<LocationSelectorState, LocationSelectorEvent, Nothing, LocationSelectorOperation>() {

    override fun reduce(event: LocationSelectorEvent) {
        when (event) {
            is LocationSelectorEvent.LoadingStatus -> state {
                copy(
                    loadingState = event.loadingState.mapList {
                        LocationModel(
                            id = it.id,
                            title = it.title,
                            address = it.address,
                            distance = it.distance
                        )
                    }
                )
            }
            is LocationSelectorUiEvent -> reduceUiEvent(event)
        }
    }

    private fun reduceUiEvent(event: LocationSelectorUiEvent) {
        when (event) {
            is LoadLocations -> operations(Load(BigDecimal.TEN, BigDecimal.ONE))
            is LocationClicked -> state {
                val locations = loadingState.data ?: emptyList()
                copy(selectedLocation = locations.find { it.id == event.id })
            }
        }
    }
}
