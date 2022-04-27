package ru.inteam.touristo.ui_kit.images

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import coil.imageLoader
import coil.request.ImageRequest
import ru.inteam.touristo.BuildConfig
import ru.inteam.touristo.R
import java.util.concurrent.atomic.AtomicBoolean

private const val TAG = "ContentImageView"

class ContentImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {
    private var onLoadListener: ((ContentImageView) -> Unit)? = null
    private val isRendering = AtomicBoolean(false)

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet,
            intArrayOf(android.R.attr.adjustViewBounds)
        )
        adjustViewBounds = attrs.getBoolean(0, true)
        attrs.recycle()
    }

    fun setOnLoadListener(listener: (ContentImageView) -> Unit) {
        onLoadListener = listener
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isRendering.getAndSet(false)) {
            onLoadListener?.invoke(this)
        }
    }

    fun load(uri: Uri) {
        load(uri) {
            crossfade(true)
            placeholder(R.drawable.shimmer_image)
            target {
                setImageDrawable(it)
                isRendering.set(true)
            }
        }
    }

    fun load(uri: Uri, config: ImageRequest.Builder.() -> Unit) {
        val request = ImageRequest.Builder(context)
            .data(uri)
            .listener(onError = { _, result ->
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, result.throwable.stackTraceToString())
                }
            })
            .apply(config)
            .build()
        context.imageLoader.enqueue(request)
    }
}
