package ru.inteam.touristo.common_ui.images

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View.MeasureSpec.EXACTLY
import androidx.appcompat.widget.AppCompatImageView
import coil.imageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import ru.inteam.touristo.common.util.min
import ru.inteam.touristo.common_ui.BuildConfig
import ru.inteam.touristo.common_ui.R
import kotlin.math.min

private const val TAG = "ContentImageView"

class ContentImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {
    private var currentLoad: Disposable? = null
    private var onLoadListeners: MutableList<OnLoadListener> = mutableListOf()
    private var isRendering = false
    private var isSquare: Boolean
    private var isSmoothRender: Boolean

    init {
        val adjustViewBoundsAttr = context.obtainStyledAttributes(
            attributeSet,
            intArrayOf(android.R.attr.adjustViewBounds)
        )
        val customAttrs = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.ContentImageView,
            defStyleAttr,
            0
        )
        val scaleTypeAttrs = context.obtainStyledAttributes(
            attributeSet,
            intArrayOf(android.R.attr.scaleType)
        )
        adjustViewBounds = adjustViewBoundsAttr.getBoolean(0, true)
        isSquare = customAttrs.getBoolean(R.styleable.ContentImageView_square, false)
        isSmoothRender = customAttrs.getBoolean(R.styleable.ContentImageView_smoothRender, true)
        if (isSquare) {
            val scaleTypeIndex = scaleTypeAttrs.getInt(0, 6)
            scaleType = ScaleType.values()[scaleTypeIndex]
        }
        scaleTypeAttrs.recycle()
        adjustViewBoundsAttr.recycle()
        customAttrs.recycle()
    }

    fun addOnLoadListener(listener: OnLoadListener?) {
        if (listener != null && listener !in onLoadListeners) {
            onLoadListeners.add(listener)
        }
    }

    fun removeOnLoadListener(listener: OnLoadListener) {
        onLoadListeners.removeAll { it == listener }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!isSquare) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        val width = MeasureSpec.getSize(widthMeasureSpec).takeIf { it > 0 }
        val height = MeasureSpec.getSize(heightMeasureSpec).takeIf { it > 0 }

        val size = min(width, height) ?: 0
        setMeasuredDimension(size, size)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isRendering) {
            isRendering = false
            var i = 0
            while (i < onLoadListeners.size) {
                onLoadListeners[i].invoke(this)
                i++
            }
        }
    }

    fun load(uri: Uri) {
        load(uri) {
            if (isSmoothRender) {
                placeholder(R.drawable.placeholder_image)
            }
            crossfade(isSmoothRender)
        }
    }

    fun load(uri: Uri, config: ImageRequest.Builder.() -> Unit) {
        currentLoad?.dispose()
        val requestBuilder = ImageRequest.Builder(context)
            .data(uri)
            .listener(
                onStart = {
                    if (!isSmoothRender) {
                        setImageResource(R.drawable.placeholder_image)
                    }
                },
                onSuccess = { _, _ -> isRendering = true },
                onError = { _, result ->
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, result.throwable.stackTraceToString())
                    }
                }
            )
            .apply(config)
        if (isSmoothRender) {
            requestBuilder.target(this)
        } else {
            requestBuilder.target {
                isRendering = true
                setImageDrawable(it)
            }
        }
        currentLoad = context.imageLoader.enqueue(requestBuilder.build())
    }

    fun interface OnLoadListener {
        operator fun invoke(view: ContentImageView)
    }
}
