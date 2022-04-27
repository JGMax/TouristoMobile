package ru.inteam.touristo

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.inteam.touristo.common.ui.network.NetworkAppCompatActivity
import ru.inteam.touristo.common.util.buildResourceUri
import ru.inteam.touristo.ui_kit.carousel.CarouselView
import ru.inteam.touristo.ui_kit.carousel.model.CarouselItem

class MainActivity : NetworkAppCompatActivity(R.layout.activity_main) {
    private val list: List<CarouselItem> by lazy {
        listOf(
            CarouselItem(buildResourceUri(R.drawable.test_photo)),
            CarouselItem(buildResourceUri(R.drawable.very_test_photo)),
            CarouselItem(buildResourceUri(R.drawable.very_test_photo)),
            CarouselItem(buildResourceUri(R.drawable.very_test_photo)),
            CarouselItem(buildResourceUri(R.drawable.very_test_photo)),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val carousel = findViewById<CarouselView>(R.id.carousel)
        lifecycleScope.launch {
//            carousel.submitItems(
//                listOf(
//                    CarouselItem {
//                        ResourcesCompat.getDrawable(
//                            resources,
//                            R.drawable.test_photo,
//                            theme
//                        )!!.toBitmap()
//                    }
//                )
//            )
//            delay(5000)
            carousel.submitItems(list)
        }
    }
}
