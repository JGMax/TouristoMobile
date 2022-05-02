package ru.inteam.touristo

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.annotation.Keep
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
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
import ru.inteam.touristo.common.ui.network.NetworkAppCompatActivity
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
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES) // read timeout
            .build()
        val api = Retrofit.Builder()
            .baseUrl("https://touristo-app.herokuapp.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Test::class.java)

        val stream = ByteArrayOutputStream()
        val d = AppCompatResources.getDrawable(this, R.drawable.test_photo)
        val bitmap = (d as BitmapDrawable).bitmap
        lifecycleScope.launch {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bitmapdata: ByteArray = stream.toByteArray()
            Log.e("bitmap", bitmapdata.size.toString())
            val requestFile: RequestBody =
                bitmapdata.toRequestBody("multipart/form-data".toMediaTypeOrNull(), 0, bitmapdata.size)

            val body = MultipartBody.Part.createFormData("image", "image", requestFile)
            //api.createUser(body)
        }
    }
}
