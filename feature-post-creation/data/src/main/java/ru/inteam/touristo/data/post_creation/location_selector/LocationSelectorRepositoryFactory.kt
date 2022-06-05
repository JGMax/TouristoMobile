package ru.inteam.touristo.data.post_creation.location_selector

import androidx.lifecycle.ViewModelStoreOwner
import ru.inteam.touristo.common_data.remote.storage.CoroutineDataStorage
import ru.inteam.touristo.common_media.shared_media.util.value
import ru.inteam.touristo.data.post_creation.location_selector.api.LocationSelectorApi
import ru.inteam.touristo.data.post_creation.location_selector.api.model.LocationSelectorLocationModel

class LocationSelectorRepositoryFactory internal constructor(
    private val api: LocationSelectorApi
) {

    companion object {
        const val LAT_ARG = "lat"
        const val LNG_ARG = "lng"
    }

    fun create(owner: ViewModelStoreOwner): CoroutineDataStorage<List<LocationSelectorLocationModel>> {
        return CoroutineDataStorage(
            source = { api.getClosestLocations(LAT_ARG.value(), LNG_ARG.value()) },
            viewModelStoreOwner = owner
        )
    }
}
