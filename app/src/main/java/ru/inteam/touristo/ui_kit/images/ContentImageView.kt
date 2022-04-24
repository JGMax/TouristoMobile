package ru.inteam.touristo.ui_kit.images

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import coil.imageLoader
import coil.request.ImageRequest
import java.util.concurrent.atomic.AtomicBoolean

private const val TAG = "ContentImageView"

class ContentImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {
    private var onLoadListener: ((ContentImageView) -> Unit)? = null
    private val isLoading = AtomicBoolean(false)

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
        if (isLoading.getAndSet(false)) {
            onLoadListener?.invoke(this)
        }
    }

    fun load(uri: Uri) {
        load(uri) {
            crossfade(true)
            target {
                setImageDrawable(it)
                onLoadListener?.invoke(this@ContentImageView)
            }
        }
    }

    fun load(uri: Uri, config: ImageRequest.Builder.() -> Unit) {
        isLoading.set(true)
        val request = ImageRequest.Builder(context)
            .data(uri)
            .listener(onError = { _, result -> Log.e(TAG, result.throwable.stackTraceToString()) })
            .apply(config)
            .build()
        context.imageLoader.enqueue(request)
    }
}
