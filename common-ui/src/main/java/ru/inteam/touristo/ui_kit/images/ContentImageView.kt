package ru.inteam.touristo.ui_kit.images

import android.animation.Animator
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import coil.imageLoader
import coil.request.ImageRequest
import ru.inteam.touristo.ui_kit.BuildConfig
import ru.inteam.touristo.ui_kit.R
import ru.inteam.touristo.ui_kit.shimmer.ShimmerView
import ru.inteam.touristo.ui_kit.shimmer.shimmerAnimator
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.min

private const val TAG = "ContentImageView"

class ContentImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr), ShimmerView {
    private var onLoadListener: ((ContentImageView) -> Unit)? = null
    private val isRendering = AtomicBoolean(false)
    override var anim: Animator? = shimmerAnimator()
    private var isLoaded = false
    private val isSquare: Boolean

    init {
        attachViewToShimmer(this)
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
        if (isSquare) {
            val scaleTypeIndex = scaleTypeAttrs.getInt(0, 6)
            scaleType = ScaleType.values()[scaleTypeIndex]
        }
        scaleTypeAttrs.recycle()
        adjustViewBoundsAttr.recycle()
        customAttrs.recycle()
    }

    override fun onVisibilityChanged(changedView: View, prevVisibility: Int) {
        visibilityChanged(prevVisibility, visibility)
        super.onVisibilityChanged(changedView, visibility)
    }

    fun setOnLoadListener(listener: (ContentImageView) -> Unit) {
        if (isLoaded) listener(this)
        onLoadListener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!isSquare) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        val size = min(width, height)
        setMeasuredDimension(size, size)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isRendering.getAndSet(false)) {
            onLoadListener?.invoke(this)
            println("xxx: $width $height")
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
            .listener(
                onStart = {
                    startShimmer()
                    isLoaded = false
                },
                onSuccess = { _, _ ->
                    stopShimmer()
                    isLoaded = true
                },
                onError = { _, result ->
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, result.throwable.stackTraceToString())
                    }
                    stopShimmer()
                    isLoaded = true
                }
            )
            .apply(config)
            .build()
        context.imageLoader.enqueue(request)
    }

    override fun stopShimmer() {
        super.stopShimmer()
        alpha = 1.0f
    }
}
