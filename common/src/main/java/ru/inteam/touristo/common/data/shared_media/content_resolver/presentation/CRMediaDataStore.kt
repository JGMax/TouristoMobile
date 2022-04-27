package ru.inteam.touristo.common.data.shared_media.content_resolver.presentation

import android.content.ContentResolver
import ru.inteam.touristo.common.data.shared_media.content_resolver.presentation.actors.CRGetMediaActor
import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.store.factory.TeaStoreFactory

typealias CRMediaDataStore = Store<CRMediaDataState, CRMediaDataEvent, Nothing>

class CRMediaDataStoreFactory(
    contentResolver: ContentResolver
) : TeaStoreFactory<CRMediaDataState, CRMediaDataEvent, Nothing, CRMediaDataOperation>(
    initialState = CRMediaDataState(),
    actors = listOf(CRGetMediaActor(contentResolver)),
    reducer = CRMediaDataReducer()
)
