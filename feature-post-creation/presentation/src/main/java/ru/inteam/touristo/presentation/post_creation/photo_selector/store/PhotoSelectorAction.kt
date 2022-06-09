package ru.inteam.touristo.presentation.post_creation.photo_selector.store

sealed class PhotoSelectorAction {
    class SelectedPhotosLimit(val limit: Int) : PhotoSelectorAction()
}
