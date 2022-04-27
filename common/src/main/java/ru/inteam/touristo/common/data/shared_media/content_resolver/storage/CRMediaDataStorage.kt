package ru.inteam.touristo.common.data.shared_media.content_resolver.storage

import android.content.ContentResolver
import android.content.ContentResolver.*
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import ru.inteam.touristo.common.data.shared_media.SharedMediaDataStorage
import ru.inteam.touristo.common.data.shared_media.content_resolver.presentation.CRMediaDataEvent
import ru.inteam.touristo.common.data.shared_media.content_resolver.presentation.CRMediaDataStore
import ru.inteam.touristo.common.data.shared_media.content_resolver.presentation.CRMediaDataStoreFactory
import ru.inteam.touristo.common.data.shared_media.model.media.MediaResponse
import ru.inteam.touristo.common.data.shared_media.model.media.MediaSelector
import ru.inteam.touristo.common.data.state.LoadingState
import ru.inteam.touristo.common.tea.collection.collect
import ru.inteam.touristo.common.tea.store.factory.TeaStore
import ru.inteam.touristo.common.tea.store.factory.TeaStoreOwner
import java.io.InputStream

private const val CR_MEDIA_DATA_STORE_KEY = "CRMediaDataStorage"

class CRMediaDataStorage(
    private val contentResolver: ContentResolver,
    viewModelStoreOwner: ViewModelStoreOwner? = null
) : TeaStoreOwner, SharedMediaDataStorage {

    private val storeOwner = viewModelStoreOwner ?: this

    private val store: CRMediaDataStore by TeaStore(storeOwner, CR_MEDIA_DATA_STORE_KEY) {
        CRMediaDataStoreFactory(contentResolver)
    }
    private val stateFlow =
        MutableSharedFlow<LoadingState<List<MediaResponse>>>(extraBufferCapacity = 10)
    private val state = stateFlow.asSharedFlow()

    init {
        store.collect(store.storeScope, { it.loadingState }, stateFlow::tryEmit)
    }

    override fun data(mediaSelector: MediaSelector?): Flow<LoadingState<List<MediaResponse>>> {
        update(mediaSelector)
        return state
    }

    override suspend fun openContentUri(uri: Uri): InputStream = withContext(Dispatchers.IO) {
        if (uri.scheme == SCHEME_CONTENT) {
            @Suppress("BlockingMethodInNonBlockingContext")
            contentResolver.openInputStream(uri) ?: throw NoSuchFieldException("Unknown Uri")
        }
        throw IllegalArgumentException("Incorrect Uri scheme")
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override suspend fun getImageBitmap(uri: Uri): Bitmap = withContext(Dispatchers.IO) {
        if (uri.scheme !in setOf(SCHEME_CONTENT, SCHEME_ANDROID_RESOURCE, SCHEME_FILE)) {
            val source = ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        throw IllegalArgumentException("Incorrect Uri scheme")
    }

    private fun update(mediaSelector: MediaSelector?) {
        if (mediaSelector != null) {
            store.dispatch(CRMediaDataEvent.GetMedia(mediaSelector))
        } else {
            store.dispatch(CRMediaDataEvent.Update)
        }
    }
}
