package ru.inteam.touristo.domain.post_creation.root.store

import ru.inteam.touristo.common.tea.Store
import ru.inteam.touristo.common.tea.store.factory.TeaStoreFactory
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationNextScreenProvider
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationPrevScreenProvider

typealias PostCreationStore = Store<PostCreationState, PostCreationUiEvent, PostCreationAction>

class PostCreationStoreFactory internal constructor(
    nextScreenProvider: PostCreationNextScreenProvider,
    prevScreenProvider: PostCreationPrevScreenProvider
) : TeaStoreFactory<PostCreationState, PostCreationUiEvent, PostCreationAction, Nothing>(
    initialState = PostCreationState(),
    reducer = PostCreationReducer(nextScreenProvider, prevScreenProvider)
)
