package ru.inteam.touristo.view.post_creation.location_selector.ui.model

import ru.inteam.touristo.recycler.item.RecyclerItem

internal data class LocationSelectorUiState(
    val selected: Location,
    val items: List<RecyclerItem>
)
