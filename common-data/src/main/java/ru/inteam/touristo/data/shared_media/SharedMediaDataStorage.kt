package ru.inteam.touristo.data.shared_media

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import ru.inteam.touristo.data.shared_media.model.media.MediaResponse
import ru.inteam.touristo.data.shared_media.model.media.MediaSelector
import ru.inteam.touristo.data.state.LoadingState
import java.io.InputStream

interface SharedMediaDataStorage {
    fun data(mediaSelector: MediaSelector? = null): Flow<LoadingState<List<MediaResponse>>>

    suspend fun openContentUri(uri: Uri): InputStream
    suspend fun getImageBitmap(uri: Uri): Bitmap
}
