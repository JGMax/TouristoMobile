package ru.inteam.touristo.common.tea.store.factory

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

interface TeaStoreOwner : ViewModelStoreOwner {
    override fun getViewModelStore(): ViewModelStore = ViewModelStore()
}
