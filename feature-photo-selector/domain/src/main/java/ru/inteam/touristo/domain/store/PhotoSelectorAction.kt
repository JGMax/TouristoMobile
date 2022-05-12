package ru.inteam.touristo.domain.store

sealed class PhotoSelectorAction {
    object NavigateNext : PhotoSelectorAction()

    class SelectedPhotosLimit(val limit: Int) : PhotoSelectorAction()
}
