package ru.inteam.touristo.feature.post_creation.location_selector.ui.mapper

import android.content.Context
import ru.inteam.touristo.common.tea.UiStateMapper
import ru.inteam.touristo.common_data.state.map
import ru.inteam.touristo.domain.post_creation.location_selector.model.LocationModel
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorState
import ru.inteam.touristo.feature.post_creation.R
import ru.inteam.touristo.feature.post_creation.location_selector.ui.model.Location
import ru.inteam.touristo.feature.post_creation.location_selector.ui.model.LocationSelectorUiState
import ru.inteam.touristo.feature.post_creation.location_selector.ui.recycler.model.LocationSelectorLocationItem

internal class LocationSelectorUiStateMapper(
    private val context: Context
) : UiStateMapper<LocationSelectorState, LocationSelectorUiState> {

    override fun map(state: LocationSelectorState): LocationSelectorUiState {
        return state.loadingState.map(
            onLoading = { emptyState(state) },
            onSuccess = { successState(state, it) }
        )
    }

    private fun successState(
        state: LocationSelectorState,
        items: List<LocationModel>
    ): LocationSelectorUiState {
        return LocationSelectorUiState(
            selected = mapToSelectedLocationModel(state.selectedLocation),
            items = items.map {
                LocationSelectorLocationItem(
                    it.id,
                    it.itemTitle,
                    it.itemSubtitle,
                    context.getString(R.string.location_selector_distance, it.distance)
                )
            }
        )
    }

    private fun emptyState(state: LocationSelectorState): LocationSelectorUiState {
        return LocationSelectorUiState(
            mapToSelectedLocationModel(state.selectedLocation),
            emptyList()
        )
    }

    private fun mapToSelectedLocationModel(model: LocationModel?): Location {
        return if (model == null) {
            Location.EmptyLocation(title = context.getString(R.string.location_selector_empty_location))
        } else {
            Location.SelectedLocation(
                id = model.id,
                title = model.itemTitle,
                subtitle = model.itemSubtitle
            )
        }
    }

    private val LocationModel.itemTitle: String
        get() = title ?: address

    private val LocationModel.itemSubtitle: String
        get() = address.takeIf { title != null } ?: ""
}
