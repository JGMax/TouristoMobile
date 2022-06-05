package ru.inteam.touristo.domain.post_creation.location_selector.store

import ru.inteam.touristo.common_data.state.LoadingState
import ru.inteam.touristo.data.post_creation.location_selector.api.model.LocationSelectorLocationModel

sealed class LocationSelectorEvent {

    internal class LoadingStatus(
        val loadingState: LoadingState<List<LocationSelectorLocationModel>>
    ) : LocationSelectorEvent()
}

sealed class LocationSelectorUiEvent : LocationSelectorEvent() {
    object LoadLocations : LocationSelectorUiEvent()

    class LocationClicked(val id: String) : LocationSelectorUiEvent()
}
