package ru.inteam.touristo.domain.post_creation.photo_selector.store

sealed class PhotoSelectorAction {
    class SelectedPhotosLimit(val limit: Int) : PhotoSelectorAction()
}
