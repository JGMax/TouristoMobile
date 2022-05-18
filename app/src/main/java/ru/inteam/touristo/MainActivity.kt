package ru.inteam.touristo

import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import ru.inteam.touristo.carousel.CarouselView
import ru.inteam.touristo.carousel.model.CarouselItem
import ru.inteam.touristo.common.ui.network.NetworkAppCompatActivity
import ru.inteam.touristo.common_data.state.data
import ru.inteam.touristo.common_media.shared_media.content_resolver.storage.CRMediaDataStorage
import ru.inteam.touristo.common_media.shared_media.model.media.types.MediaType
import ru.inteam.touristo.feature.post_creation.root.ui.PostCreationActivity

class MainActivity : NetworkAppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(PostCreationActivity.intent(this))
        }
        val carousel = findViewById<CarouselView>(R.id.carousel)
        val storage = CRMediaDataStorage(contentResolver, this)

        lifecycleScope.launchWhenResumed {
            storage.data(MediaType.Images())
                .mapNotNull { it.data }
                .map { it.map { CarouselItem(it.id.toString(), it.contentUri) } }
                .onEach { carousel.submitItems(it) }
                .collect()
        }
    }
}
