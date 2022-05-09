package ru.inteam.touristo.feature.photo_selector.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.get
import ru.inteam.touristo.common.tea.collection.collect
import ru.inteam.touristo.common.tea.store.factory.TeaStore
import ru.inteam.touristo.common.ui.activity.intentFor
import ru.inteam.touristo.common.ui.permission.hasPermission
import ru.inteam.touristo.common.ui.permission.permissionDelegate
import ru.inteam.touristo.common.ui.toolbar.attachToolbar
import ru.inteam.touristo.feature.photo_selector.R
import ru.inteam.touristo.feature.photo_selector.databinding.PhotoSelectorActivityBinding
import ru.inteam.touristo.domain.store.PhotoSelectorStore
import ru.inteam.touristo.domain.store.PhotoSelectorStoreFactory
import ru.inteam.touristo.domain.store.PhotoSelectorUiEvent.ImageClicked
import ru.inteam.touristo.domain.store.PhotoSelectorUiEvent.LoadAll
import ru.inteam.touristo.feature.photo_selector.ui.mapper.PhotoSelectorUiStateMapper
import ru.inteam.touristo.feature.photo_selector.ui.model.PhotoSelectorUiState
import ru.inteam.touristo.feature.photo_selector.ui.recycler.PhotoSelectorImageItem
import ru.inteam.touristo.feature.photo_selector.ui.recycler.decorations.PhotoSelectorItemDecoration
import ru.inteam.touristo.recycler.RecyclerManager
import ru.inteam.touristo.recycler.clicks.clicks
import ru.inteam.touristo.recycler.diff_util.DefaultDiffCallback
import ru.inteam.touristo.recycler.managerBuilder

class PhotoSelectorActivity : AppCompatActivity(R.layout.photo_selector_activity) {

    companion object {
        fun intent(context: Context) = intentFor<PhotoSelectorActivity>(context)
    }

    private val store: PhotoSelectorStore by TeaStore { get<PhotoSelectorStoreFactory>() }
    private val binding: PhotoSelectorActivityBinding by viewBinding()
    private lateinit var recycler: RecyclerManager

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
            .diffCallback(DefaultDiffCallback())
            .layoutManager(GridLayoutManager(this, 4))
            .build()

        lifecycleScope.launchWhenResumed {
            recycler.clicks<PhotoSelectorImageItem>(R.id.image)
                .onEach { store.dispatch(ImageClicked(it.source)) }
                .collect()
        }
    }

    private fun render(state: PhotoSelectorUiState) {
        state.buckets.forEach {
            println("xxx: $it")
        }
        println("xxx ${state.selected}")
        binding.carousel.submitItems(state.selected)
        recycler.submitList(state.content)
    }
}