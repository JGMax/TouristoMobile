package ru.inteam.touristo.view.post_creation.location_selector.ui.model

internal sealed class Location(val title: String) {

    class SelectedLocation(
        val id: String,
        title: String,
        val subtitle: String
    ) : Location(title)

    class EmptyLocation(
        title: String
    ) : Location(title)
}
