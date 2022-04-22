package ru.inteam.touristo

import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.inteam.touristo.common.ui.network.NetworkAppCompatActivity
import ru.inteam.touristo.common.ui.recycler.item.RecyclerItem
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.ui_components.carousel.CarouselView
import ru.inteam.touristo.ui_components.carousel.model.CarouselItem

class MainActivity : NetworkAppCompatActivity(R.layout.activity_main) {
    private val list =
        listOf(
            CarouselItem {
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.test_photo,
                    theme
                )!!.toBitmap()
            },
            CarouselItem {
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.very_test_photo,
                    theme
                )!!.toBitmap()
            },
            CarouselItem {
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.very_test_photo,
                    theme
                )!!.toBitmap()
            },
            CarouselItem {
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.very_test_photo,
                    theme
                )!!.toBitmap()
            },
            CarouselItem {
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.very_test_photo,
                    theme
                )!!.toBitmap()
            },
        )

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
            launch {
                carousel.clicks()
                    .onEach {
                        if (it.view.get()?.id == R.id.indicator) {
                            carousel.showCarousel()
                        } else {
                            carousel.showItem(it.item as CarouselItem)
                        }
                    }
                    .collect()
            }
        }
    }
}
