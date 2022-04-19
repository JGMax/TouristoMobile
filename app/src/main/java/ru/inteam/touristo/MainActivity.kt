package ru.inteam.touristo

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.inteam.touristo.common.ui.network.NetworkAppCompatActivity
import ru.inteam.touristo.ui_components.carousel.CarouselView
import ru.inteam.touristo.ui_components.carousel.model.CarouselItem

class MainActivity : NetworkAppCompatActivity(R.layout.activity_main) {

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
            carousel.submitItems(
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
            )
        }
    }
}
