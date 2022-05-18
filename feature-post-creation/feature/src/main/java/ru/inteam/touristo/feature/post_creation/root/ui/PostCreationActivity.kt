package ru.inteam.touristo.feature.post_creation.root.ui

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get
import ru.inteam.touristo.common.navigation.NavigationOwner
import ru.inteam.touristo.common.navigation.NavigationStore
import ru.inteam.touristo.common.tea.collection.collect
import ru.inteam.touristo.common.tea.store.factory.TeaStore
import ru.inteam.touristo.common.ui.activity.intentFor
import ru.inteam.touristo.common.ui.toolbar.attachToolbar
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationNavigation
import ru.inteam.touristo.domain.post_creation.root.navigation.PostCreationScreen
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationAction
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationStore
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationStoreFactory
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationUiEvent.*
import ru.inteam.touristo.feature.post_creation.R
import ru.inteam.touristo.feature.post_creation.databinding.PostCreationActivityBinding
import ru.inteam.touristo.feature.post_creation.root.ui.mapper.PostCreationUiStateMapper
import ru.inteam.touristo.feature.post_creation.root.ui.model.PostCreationUiState

class PostCreationActivity : AppCompatActivity(R.layout.post_creation_activity),
    NavigationOwner<PostCreationScreen> {

    companion object {
        fun intent(context: Context) = intentFor<PostCreationActivity>(context)
        const val STORE_KEY = "POST_CREATION_ROOT_STORE_KEY"
    }

    private val store: PostCreationStore by TeaStore(STORE_KEY) { get<PostCreationStoreFactory>() }
    private val binding: PostCreationActivityBinding by viewBinding()

    override val navigation: PostCreationNavigation by NavigationStore(R.id.container) { get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store.collect(lifecycleScope, get<PostCreationUiStateMapper>(), ::render, ::handleAction)
        store.dispatch(OpenCurrentScreen)
        attachToolbar(binding.toolbar)
        initViews()
    }

    private fun initViews() {
        lifecycleScope.launchWhenResumed {
            binding.accept.clicks()
                .onEach { store.dispatch(OpenNextScreen) }
                .launchIn(this)

            binding.selectionButton.clicks()
                .onEach { store.dispatch(ChangeSelectionStyle) }
                .launchIn(this)
        }
    }

    private fun render(state: PostCreationUiState) {
        binding.carousel.submitItems(state.selected)
        binding.selectionButton.backgroundTintList =
            ColorStateList.valueOf(state.selectionButtonTint)
    }

    private fun handleAction(action: PostCreationAction) {
        when (action) {
            is PostCreationAction.OpenScreen -> navigation.openScreen(action.screen)
        }
    }
}
