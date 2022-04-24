package ru.inteam.touristo.ui_kit.carousel

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.*
import ru.inteam.touristo.R
import ru.inteam.touristo.common.ui.recycler.RecyclerManager
import ru.inteam.touristo.common.ui.recycler.clicks.ClickEvent.ItemClick
import ru.inteam.touristo.common.ui.recycler.managerBuilder
import ru.inteam.touristo.common.ui.view.reactive.clicks
import ru.inteam.touristo.common.ui.view.viewScope
import ru.inteam.touristo.ui_kit.carousel.decorations.CarouselCornersItemDecoration
import ru.inteam.touristo.ui_kit.carousel.decorations.CarouselMarginsItemDecoration
import ru.inteam.touristo.ui_kit.carousel.model.CarouselItem
import ru.inteam.touristo.ui_kit.images.ContentImageView
import java.lang.ref.WeakReference
import kotlin.math.abs


class CarouselView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr) {
    private val recycler: RecyclerView
    private val image: ContentImageView
    private val recyclerManager: RecyclerManager
    private val snapHelper: LinearSnapHelper
    private val recyclerLayoutParams =
        LayoutParams(MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.carousel_height))
    private val indicator: ImageView

    private val outerClicks = MutableSharedFlow<ItemClick>(extraBufferCapacity = 10)
    private var currentFullItem: CarouselItem? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.ui_component_carousel, this, true)
        recycler = findViewById(R.id.recycler)
        recycler.layoutParams = recyclerLayoutParams
        snapHelper = LinearSnapHelper().apply { attachToRecyclerView(recycler) }
        image = findViewById(R.id.image)
        indicator = findViewById(R.id.indicator)
        val horizontalLM = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerManager = recycler.managerBuilder()
            .layoutManager(CarouselLayoutManager(context))
            .decorations(CarouselMarginsItemDecoration(), CarouselCornersItemDecoration())
            .build()

        recyclerManager.clicks<CarouselItem>()
            .onEach(::onCarouselItemClicked)
            .launchIn(viewScope)

        indicator.clicks()
            .mapNotNull { view -> currentFullItem?.let { ItemClick(it, WeakReference(view)) } }
            .onEach(outerClicks::tryEmit)
            .launchIn(viewScope)
    }

    fun clicks(): Flow<ItemClick> = outerClicks.asSharedFlow()

    private fun onCarouselItemClicked(click: ItemClick) {
        val view = click.view.get() ?: return
        val focusedView = snapHelper.findSnapView(recycler.layoutManager) ?: return
        if (view == focusedView) {
            outerClicks.tryEmit(click)
        } else {
            val position = recycler.getChildAdapterPosition(view)
            val prevPosition = recycler.getChildAdapterPosition(focusedView)
            val min = position.coerceAtMost(prevPosition) + 1
            val max = position.coerceAtLeast(prevPosition) - 1
            val outRect = Rect()
            recycler.getDecoratedBoundsWithMargins(focusedView, outRect)
            var desiredScroll = getSideElementsDesiredScroll(prevPosition, focusedView, outRect)
            recycler.getDecoratedBoundsWithMargins(view, outRect)
            desiredScroll += getSideElementsDesiredScroll(position, view, outRect)
            for (i in min until max) {
                recycler.getDecoratedBoundsWithMargins(recycler.getChildAt(i), outRect)
                desiredScroll += outRect.width()
            }

            val k = (position - prevPosition) / abs(position - prevPosition)
            recycler.smoothScrollBy(k * desiredScroll, 0)
        }
    }

    private fun getSideElementsDesiredScroll(position: Int, view: View, outRect: Rect): Int {
        return when (position) {
            0 -> view.width / 2 + outRect.right - view.right
            recyclerManager.itemCount - 1 -> view.width / 2 + view.left - outRect.left
            else -> outRect.width() / 2
        }
    }

    fun showCarousel() {
        image.isGone = true
        indicator.isGone = true
        currentFullItem = null
        recycler.isVisible = true
    }

    fun submitItems(items: List<CarouselItem>) {
        showCarousel()
        val prevItemCount = recyclerManager.itemCount
        recyclerManager.submitList(items) {
            if (recyclerManager.itemCount != 0) {
                when {
                    prevItemCount != 0 -> {
                        recycler.scrollToPosition(recyclerManager.itemCount - 1)
                    }
                    prevItemCount != recyclerManager.itemCount -> {
                        recycler.scrollToPosition(0)
                    }
                }
            }
        }
    }

    fun showItem(item: CarouselItem) {
        showFullImage(item)
    }

    private fun showFullImage(item: CarouselItem) {
        recycler.isGone = true
        image.isVisible = true
        indicator.isVisible = true
        currentFullItem = item
        setIndicatorPosition()
        image.load(item.source)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val desiredHeight = if (recycler.isVisible) {
            measureChild(recycler, widthMeasureSpec, heightMeasureSpec)
            recycler.measuredHeight
        } else {
            measureChild(image, widthMeasureSpec, heightMeasureSpec)
            image.measuredHeight
        }
        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (recycler.isVisible) {
            recycler.layout(l, t, r, b)
        } else {
            image.layout(l, t, r, b)
        }
    }

    private fun setIndicatorPosition() {
//        measureChild(indicator, MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
//        indicator.layout(
//            image.right - indicator.measuredWidth,
//            image.bottom - indicator.measuredHeight,
//            image.right,
//            image.bottom
//        )
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(MATCH_PARENT, MATCH_PARENT)
    }

    override fun generateLayoutParams(attributeSet: AttributeSet?): LayoutParams {
        return MarginLayoutParams(MATCH_PARENT, MATCH_PARENT)
    }
}
