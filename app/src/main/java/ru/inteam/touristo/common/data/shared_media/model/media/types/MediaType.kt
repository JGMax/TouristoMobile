package ru.inteam.touristo.common.data.shared_media.model.media.types

import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import ru.inteam.touristo.common.data.shared_media.model.media.MediaField
import ru.inteam.touristo.common.data.shared_media.model.media.MediaSelector
import ru.inteam.touristo.common.data.shared_media.model.media.Selector
import ru.inteam.touristo.common.data.shared_media.model.media.SortOrder

sealed class MediaType(
    override val sortOrder: SortOrder = SortOrder.none,
    override val selector: Selector = Selector.none
) : MediaSelector {

    class Images(
        requiredFields: List<MediaField<*>>,
        selector: Selector = Selector.none
    ) : MediaType(
        SortOrder.asc(MediaStore.Images.Media.DATE_MODIFIED),
        selector
    ) {

        override val projection: List<MediaField<*>> = requiredFields

        override val collection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
    }
}
