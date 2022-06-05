package ru.inteam.touristo.data.post_creation.location_selector.api.model

import androidx.annotation.Keep
import java.math.BigDecimal

@Keep
class LocationSelectorLocationModel(
    val id: String,
    val lat: BigDecimal,
    val lng: BigDecimal,
    val distance: Double,
    val title: String?,
    val address: String
)
