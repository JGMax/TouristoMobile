package ru.inteam.touristo.feature.post_creation.root.ui.mapper

import android.content.Context
import ru.inteam.touristo.carousel.model.CarouselItem
import ru.inteam.touristo.common.tea.UiStateMapper
import ru.inteam.touristo.common_ui.R
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationState
import ru.inteam.touristo.feature.post_creation.root.ui.model.PostCreationUiState

internal class PostCreationUiStateMapper(
    context: Context
) : UiStateMapper<PostCreationState, PostCreationUiState> {

    private val multiSelectionEnabledButtonTint = context.getColor(R.color.main_100)
    private val multiSelectionDisabledButtonTint = context.getColor(R.color.base_0)

    override fun map(state: PostCreationState): PostCreationUiState {
        return PostCreationUiState(
            selected = state.selected.map { CarouselItem(it.id, it.uri) },
            selectionButtonTint = multiSelectionEnabledButtonTint.takeIf {
                state.isMultiSelection
            } ?: multiSelectionDisabledButtonTint,
            screen = state.screen
        )
    }
}
