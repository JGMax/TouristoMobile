package ru.inteam.touristo.data.post_creation.location_selector.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.inteam.touristo.data.post_creation.location_selector.api.model.LocationSelectorLocationModel
import java.math.BigDecimal
import kotlin.math.sqrt
import kotlin.random.Random

interface LocationSelectorApi {

    @GET("locations")
    fun getClosestLocations(
        @Query("lat") lat: BigDecimal,
        @Query("lng") lng: BigDecimal
    ): List<LocationSelectorLocationModel>
}

internal class LocationsSelectorApiImpl : LocationSelectorApi {

    override fun getClosestLocations(
        lat: BigDecimal,
        lng: BigDecimal
    ): List<LocationSelectorLocationModel> = listOf(
        generateLocation(lat, lng),
        generateLocation(lat, lng),
        generateLocation(lat, lng),
        generateLocation(lat, lng),
        generateLocation(lat, lng),
        generateLocation(lat, lng)
    )

    private fun generateLocation(lat: BigDecimal, lng: BigDecimal): LocationSelectorLocationModel {
        val mLat = Random.nextDouble().toBigDecimal()
        val mLng = Random.nextDouble().toBigDecimal()
        val id = (Random.nextInt(0, 10)).toString()
        return LocationSelectorLocationModel(
            id = id,
            lat = mLat,
            lng = mLng,
            distance = sqrt(((mLat - lat).pow(2) - (mLng - lng).pow(2)).toDouble()),
            title = if (Random.nextBoolean()) "Локация id: $id" else null,
            address = "ул. Пушкина, дом $id"
        )
    }
}
