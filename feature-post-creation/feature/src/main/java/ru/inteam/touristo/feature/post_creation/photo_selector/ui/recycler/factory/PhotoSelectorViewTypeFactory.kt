package ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.factory

import ru.inteam.touristo.feature.post_creation.R
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.model.PhotoSelectorImageItemViewType
import ru.inteam.touristo.recycler.holder.ViewType
import ru.inteam.touristo.recycler.holder.ViewTypeFactory

internal class PhotoSelectorViewTypeFactory : ViewTypeFactory {

    override fun createViewType(viewType: Int): ViewType? {
        return when(viewType) {
            R.layout.photo_selector_photo_item -> PhotoSelectorImageItemViewType()
            else -> null
        }
    }
}
