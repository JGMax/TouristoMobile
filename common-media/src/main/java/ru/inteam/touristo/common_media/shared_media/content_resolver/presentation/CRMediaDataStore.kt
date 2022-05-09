package ru.inteam.touristo.common_media.shared_media.content_resolver.presentation

import android.content.ContentResolver
import ru.inteam.touristo.common_media.shared_media.content_resolver.presentation.actors.CRGetMediaActor
import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.store.factory.TeaStoreFactory

internal typealias CRMediaDataStore = Store<CRMediaDataState, CRMediaDataEvent, Nothing>

internal class CRMediaDataStoreFactory(
    contentResolver: ContentResolver
) : TeaStoreFactory<CRMediaDataState, CRMediaDataEvent, Nothing, CRMediaDataOperation>(
    initialState = CRMediaDataState(),
    actors = listOf(CRGetMediaActor(contentResolver)),
    reducer = CRMediaDataReducer()
)
