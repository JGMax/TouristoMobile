package ru.inteam.touristo.feature.post_creation.photo_selector.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get
import ru.inteam.touristo.common.tea.collection.collect
import ru.inteam.touristo.common.tea.store.factory.ActivityTeaStore
import ru.inteam.touristo.common.tea.store.factory.TeaStore
import ru.inteam.touristo.common.ui.fragment.viewLifecycleScope
import ru.inteam.touristo.common.ui.permission.hasPermission
import ru.inteam.touristo.common.ui.permission.permissionDelegate
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.common.util.toast
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorAction
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorStore
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorStoreFactory
import ru.inteam.touristo.domain.post_creation.photo_selector.store.PhotoSelectorUiEvent.*
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationStore
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationStoreFactory
import ru.inteam.touristo.domain.post_creation.root.store.PostCreationUiEvent.Selected
import ru.inteam.touristo.feature.post_creation.R
import ru.inteam.touristo.feature.post_creation.databinding.PhotoSelectorFragmentBinding
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.mapper.PhotoSelectorRootUiStateMapper
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.mapper.PhotoSelectorUiStateMapper
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.model.PhotoSelectorRootUiState
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.model.PhotoSelectorUiState
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.decorations.PhotoSelectorItemDecoration
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.diff.PhotoSelectorDiffCallback
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.factory.PhotoSelectorViewTypeFactory
import ru.inteam.touristo.feature.post_creation.photo_selector.ui.recycler.model.PhotoSelectorImageItem
import ru.inteam.touristo.feature.post_creation.root.ui.PostCreationActivity.Companion.STORE_KEY
import ru.inteam.touristo.recycler.RecyclerManager
import ru.inteam.touristo.recycler.clicks.clicks
import ru.inteam.touristo.recycler.manager
import ru.inteam.touristo.common_ui.R as Common_uiR

class PhotoSelectorFragment : Fragment(R.layout.photo_selector_fragment) {

    companion object {
        fun instance() = PhotoSelectorFragment()
    }

    private val store: PhotoSelectorStore by TeaStore { get<PhotoSelectorStoreFactory>() }
    private val rootStore: PostCreationStore by ActivityTeaStore(STORE_KEY) {
        get<PostCreationStoreFactory>()
    }

    private val binding: PhotoSelectorFragmentBinding by viewBinding()
    private val materialGroupSelector: MaterialButton
        get() = binding.groupSelector as MaterialButton

    private lateinit var recycler: RecyclerManager
    private lateinit var popup: PopupMenu

    private val permissionLauncher by permissionDelegate { store.dispatch(LoadAll) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

        store.collect(viewLifecycleScope, get<PhotoSelectorUiStateMapper>(), ::render, ::dispatch)
        rootStore.collect(viewLifecycleScope, get<PhotoSelectorRootUiStateMapper>(), ::rootRender)

        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            store.dispatch(LoadAll)
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun initViews() {
        recycler = binding.recycler.manager {
            decorations(PhotoSelectorItemDecoration())
            diffCallback(PhotoSelectorDiffCallback())
            viewTypeFactory(PhotoSelectorViewTypeFactory())
            layoutManager(GridLayoutManager(requireContext(), 4))
        }

        popup = PopupMenu(requireContext(), binding.groupSelector)

        viewLifecycleScope.launchWhenResumed {
            recycler.clicks<PhotoSelectorImageItem>(R.id.image)
                .onEach { store.dispatch(ImageClicked(it.itemId, it.source)) }
                .launchIn(this)

            materialGroupSelector.clicks()
                .onEach { popup.show() }
                .onEach { materialGroupSelector.setIconResource(Common_uiR.drawable.ic_chevron_up) }
                .launchIn(this)
        }

        popup.setOnMenuItemClickListener {
            store.dispatch(LoadBucket(it.title.toString()))
            true
        }

        popup.setOnDismissListener {
            materialGroupSelector.setIconResource(Common_uiR.drawable.ic_chevron_down)
        }
    }

    private fun render(state: PhotoSelectorUiState) {
        val prevBucket = materialGroupSelector.text
        recycler.submitList(state.content) {
            if (prevBucket != state.currentBucket) {
                binding.recycler.scrollToPosition(0)
            }
        }
        materialGroupSelector.text = state.currentBucket
        if (state.needShowChevron) {
            materialGroupSelector.setIconResource(Common_uiR.drawable.ic_chevron_down)
            materialGroupSelector.isEnabled = true
        } else {
            materialGroupSelector.icon = null
            materialGroupSelector.isEnabled = false
        }

        popup.menu.clear()
        state.buckets.forEach(popup.menu::add)

        rootStore.dispatch(Selected(state.selected))
    }

    private fun rootRender(state: PhotoSelectorRootUiState) {
        store.dispatch(ChangeIsMultiSelection(state.isMultiSelection))
    }

    private fun dispatch(action: PhotoSelectorAction) {
        when (action) {
            is PhotoSelectorAction.SelectedPhotosLimit -> toast(
                getString(R.string.photo_selector_limit_message, action.limit)
            )
        }
    }
}
