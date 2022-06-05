package ru.inteam.touristo.domain.post_creation.location_selector.store

import java.math.BigDecimal

sealed class LocationSelectorOperation {
    internal class Load(val lat: BigDecimal, val lng: BigDecimal) : LocationSelectorOperation()
}
