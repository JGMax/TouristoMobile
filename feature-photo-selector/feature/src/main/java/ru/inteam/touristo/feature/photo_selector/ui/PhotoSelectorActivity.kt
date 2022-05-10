package ru.inteam.touristo.feature.photo_selector.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import ru.inteam.touristo.common.tea.collection.collect
import ru.inteam.touristo.common.tea.store.factory.TeaStore
import ru.inteam.touristo.common.ui.activity.intentFor
import ru.inteam.touristo.common.ui.permission.hasPermission
import ru.inteam.touristo.common.ui.permission.permissionDelegate
import ru.inteam.touristo.common.ui.toolbar.attachToolbar
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.domain.store.PhotoSelectorStore
import ru.inteam.touristo.domain.store.PhotoSelectorStoreFactory
import ru.inteam.touristo.domain.store.PhotoSelectorUiEvent.*
import ru.inteam.touristo.feature.photo_selector.R
import ru.inteam.touristo.feature.photo_selector.databinding.PhotoSelectorActivityBinding
import ru.inteam.touristo.feature.photo_selector.ui.mapper.PhotoSelectorUiStateMapper
import ru.inteam.touristo.feature.photo_selector.ui.model.PhotoSelectorUiState
import ru.inteam.touristo.feature.photo_selector.ui.recycler.model.PhotoSelectorImageItem
import ru.inteam.touristo.feature.photo_selector.ui.recycler.decorations.PhotoSelectorItemDecoration
import ru.inteam.touristo.feature.photo_selector.ui.recycler.factory.PhotoSelectorViewTypeFactory
import ru.inteam.touristo.recycler.RecyclerManager
import ru.inteam.touristo.recycler.clicks.clicks
import ru.inteam.touristo.recycler.managerBuilder
import ru.inteam.touristo.common_ui.R as CommonR

class PhotoSelectorActivity : AppCompatActivity(R.layout.photo_selector_activity) {

    companion object {
        fun intent(context: Context) = intentFor<PhotoSelectorActivity>(context)
    }

    private val store: PhotoSelectorStore by TeaStore { get<PhotoSelectorStoreFactory>() }
    private val binding: PhotoSelectorActivityBinding by viewBinding()
    private lateinit var recycler: RecyclerManager
    private lateinit var popup: PopupMenu

    private val permissionLauncher by permissionDelegate { store.dispatch(LoadAll) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachToolbar(binding.toolbar)
        initViews()

        store.collect(lifecycleScope, get<PhotoSelectorUiStateMapper>(), ::render)

        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            store.dispatch(LoadAll)
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun initViews() {
        recycler = binding.recycler.managerBuilder()
            .decorations(PhotoSelectorItemDecoration())
            .viewTypeFactory(PhotoSelectorViewTypeFactory())
            .layoutManager(GridLayoutManager(this, 4))
            .build()

        popup = PopupMenu(this, binding.groupSelector)

        lifecycleScope.launchWhenResumed {
            launch {
                recycler.clicks<PhotoSelectorImageItem>(R.id.image)
                    .onEach { println("xxx: jj") }
                    .onEach { store.dispatch(ImageClicked(it.source)) }
                    .collect()
            }
//            launch {
//                recycler.clicksUnit()
//                    .onEach { println("xxx: fuck") }
//                    .flatMapMerge { it }
//                    .onEach { println("xxx: fuck") }
//                    .collect()
//            }
//            launch {
//                recycler.clicks<PhotoSelectorImageItem>(R.id.image)
//                    .onEach { println("xxx: ${it}") }
//                    .collect()
//            }
//
//            launch {
//                recycler.clicks<PhotoSelectorImageItem>(R.id.image)
//                    .onEach { println("xxx: фищиф ${it}") }
//                    .collect()
//            }

            launch {
                binding.groupSelector.clicks()
                    .onEach { popup.show() }
                    .onEach { (it as MaterialButton).setIconResource(CommonR.drawable.ic_chevron_up) }
                    .collect()
            }
        }

        popup.setOnMenuItemClickListener {
            store.dispatch(LoadBucket(it.title.toString()))
            binding.recycler.smoothScrollToPosition(0)
            true
        }

        popup.setOnDismissListener {
            (binding.groupSelector as MaterialButton).setIconResource(CommonR.drawable.ic_chevron_down)
        }
    }

    private fun render(state: PhotoSelectorUiState) {
        binding.carousel.submitItems(state.selected)
        recycler.submitList(state.content)
        binding.groupSelector.text = state.currentBucket

        popup.menu.clear()
        state.buckets.forEach(popup.menu::add)
    }
}
