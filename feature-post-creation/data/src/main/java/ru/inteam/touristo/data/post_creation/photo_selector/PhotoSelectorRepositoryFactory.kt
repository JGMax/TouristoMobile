package ru.inteam.touristo.data.post_creation.photo_selector

import android.content.ContentResolver
import androidx.lifecycle.ViewModelStoreOwner
import ru.inteam.touristo.common_media.shared_media.content_resolver.storage.CRMediaDataStorage

class PhotoSelectorRepositoryFactory(private val contentResolver: ContentResolver) {

    fun create(owner: ViewModelStoreOwner): CRMediaDataStorage {
        return CRMediaDataStorage(
            viewModelStoreOwner = owner,
            contentResolver = contentResolver
        )
    }
}
