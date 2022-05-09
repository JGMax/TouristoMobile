package ru.inteam.touristo.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.coroutines.flow.*
import ru.inteam.touristo.recycler.clicks.ClickEvent.ItemClick
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.common.ui.view.viewScope
import ru.inteam.touristo.carousel.model.CarouselItem
import ru.inteam.touristo.common_ui.images.ContentImageView
import java.lang.ref.WeakReference

class CarouselFullImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr) {
    private val image: ContentImageView
    private val indicator: ImageView
    private val listener = ContentImageView.OnLoadListener { requestLayout() }

    private val outerClicks = MutableSharedFlow<ItemClick>(extraBufferCapacity = 10)
    private var currentFullItem: CarouselItem? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.ui_kit_carousel_full_image, this, true)
        image = findViewById(R.id.image)
        indicator = findViewById(R.id.indicator)

        indicator.clicks()
            .mapNotNull { view ->
                currentFullItem?.let { ItemClick(it, WeakReference(view)) }
            }
            .onEach(outerClicks::tryEmit)
            .launchIn(viewScope)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        image.addOnLoadListener(listener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        image.removeOnLoadListener(listener)
    }

    fun clicks(): Flow<ItemClick> = outerClicks.asSharedFlow()

    fun showFullImage(item: CarouselItem) {
        currentFullItem = item
        image.load(item.source)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        measureChild(image, widthMeasureSpec, heightMeasureSpec)
        measureChild(indicator, MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        val desiredHeight = image.measuredHeight
        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        image.layout(l, t, r, b)
        indicator.layout(
            image.width - indicator.measuredWidth,
            image.height - indicator.measuredHeight,
            image.width,
            image.height
        )
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(attributeSet: AttributeSet?): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
}
