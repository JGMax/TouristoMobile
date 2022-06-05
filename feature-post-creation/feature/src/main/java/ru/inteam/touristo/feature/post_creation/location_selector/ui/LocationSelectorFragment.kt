package ru.inteam.touristo.feature.post_creation.location_selector.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get
import ru.inteam.touristo.common.tea.collection.collect
import ru.inteam.touristo.common.tea.store.factory.ActivityTeaStore
import ru.inteam.touristo.common.tea.store.factory.TeaStore
import ru.inteam.touristo.common.ui.fragment.viewLifecycleScope
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorStore
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorStoreFactory
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorUiEvent
import ru.inteam.touristo.domain.post_creation.location_selector.store.LocationSelectorUiEvent.LoadLocations
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationStore
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationStoreFactory
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationUiEvent
import ru.inteam.touristo.feature.post_creation.R
import ru.inteam.touristo.feature.post_creation.databinding.LocationSelectorFragmentBinding
import ru.inteam.touristo.feature.post_creation.location_selector.ui.mapper.LocationSelectorUiStateMapper
import ru.inteam.touristo.feature.post_creation.location_selector.ui.model.Location
import ru.inteam.touristo.feature.post_creation.location_selector.ui.model.LocationSelectorUiState
import ru.inteam.touristo.feature.post_creation.location_selector.ui.recycler.model.LocationSelectorLocationItem
import ru.inteam.touristo.feature.post_creation.root.ui.PostCreationActivity
import ru.inteam.touristo.recycler.RecyclerManager
import ru.inteam.touristo.recycler.clicks.clicks
import ru.inteam.touristo.recycler.manager

class LocationSelectorFragment : Fragment(R.layout.location_selector_fragment) {

    companion object {
        fun instance() = LocationSelectorFragment()
    }

    private val binding: LocationSelectorFragmentBinding by viewBinding()

    private val store: LocationSelectorStore by TeaStore { get<LocationSelectorStoreFactory>() }
    private val rootStore: PostCreationStore by ActivityTeaStore(PostCreationActivity.STORE_KEY) {
        get<PostCreationStoreFactory>()
    }

    private lateinit var recycler: RecyclerManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

        rootStore.dispatch(PostCreationUiEvent.HideChangeSelectionStyleButton)
        store.collect(viewLifecycleScope, LocationSelectorUiStateMapper(requireContext()), ::render)
        // todo collect and render + navigation
        // todo add action to send event to root to show check

        if (savedInstanceState == null) {
            store.dispatch(LoadLocations)
        }
    }

    private fun initViews() {
        recycler = binding.recycler.manager()

        viewLifecycleScope.launchWhenResumed {
            recycler.clicks<LocationSelectorLocationItem>(R.id.container)
                .onEach { store.dispatch(LocationSelectorUiEvent.LocationClicked(it.id)) }
                .launchIn(this)
        }
    }

    private fun render(state: LocationSelectorUiState) {
        recycler.submitList(state.items)
        with(binding) {
            val selected = state.selected
            titleTv.text = selected.title
            if (selected is Location.SelectedLocation) {
                rootStore.dispatch(PostCreationUiEvent.ShowNextButton)
                subtitleTv.text = selected.subtitle
                subtitleTv.isGone = selected.subtitle.isEmpty()
            } else {
                rootStore.dispatch(PostCreationUiEvent.HideNextButton)
                subtitleTv.isGone = true
            }
        }
    }
}
