package ru.inteam.touristo.presentation.post_creation.location_selector.store

import ru.inteam.touristo.common_data.state.LoadingState
import ru.inteam.touristo.presentation.post_creation.location_selector.model.LocationModel

data class LocationSelectorState(
    val loadingState: LoadingState<List<LocationModel>> = LoadingState.Loading.Default(),
    val selectedLocation: LocationModel? = null
)
