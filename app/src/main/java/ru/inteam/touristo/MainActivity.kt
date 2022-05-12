package ru.inteam.touristo

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.Keep
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.inteam.touristo.carousel.CarouselView
import ru.inteam.touristo.carousel.model.CarouselItem
import ru.inteam.touristo.common.ui.network.NetworkAppCompatActivity
import ru.inteam.touristo.common_data.state.data
import ru.inteam.touristo.common_media.shared_media.content_resolver.storage.CRMediaDataStorage
import ru.inteam.touristo.common_media.shared_media.model.media.types.MediaType
import ru.inteam.touristo.feature.photo_selector.ui.PhotoSelectorActivity
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit


@Keep
class User(
    val nickname: String,
    val email: String,
    val password: String,
    val photo: ByteArray
)

@Keep
data class Response(
    val id: Long,
    val nickname: String,
    val email: String,
    val password: String,
    val created: String,
    val photo: String
)

interface Test {

    @Multipart
    @POST("post")
    suspend fun createUser(@Part byteArray: MultipartBody.Part)
}

class MainActivity : NetworkAppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(PhotoSelectorActivity.intent(this))
        }
        val carousel = findViewById<CarouselView>(R.id.carousel)
        val storage = CRMediaDataStorage(contentResolver, this)

        lifecycleScope.launchWhenResumed {
            storage.data(MediaType.Images())
                .mapNotNull { it.data }
                .map { it.map { CarouselItem(it.contentUri) } }
                .onEach { carousel.submitItems(it) }
                .collect()
        }
    }
}
