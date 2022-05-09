package ru.inteam.touristo.domain.store

import android.net.Uri
import ru.inteam.touristo.common_data.state.LoadingState
import ru.inteam.touristo.domain.store.model.PhotoSelectorMedia

data class PhotoSelectorState(
    val loadingState: LoadingState<Map<String?, List<PhotoSelectorMedia>>> = LoadingState.Loading.Default(),
    val selected: List<Uri> = emptyList(),
    val currentBucket: String? = null
)
