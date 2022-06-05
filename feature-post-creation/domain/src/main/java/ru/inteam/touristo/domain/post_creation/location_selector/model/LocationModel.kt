package ru.inteam.touristo.domain.post_creation.location_selector.model

data class LocationModel(
    val id: String,
    val title: String?,
    val address: String,
    val distance: Double
)
