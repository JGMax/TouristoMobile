package ru.inteam.touristo.carousel

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.inteam.touristo.recycler.util.getViewByAdapterPosition
import ru.inteam.touristo.common.ui.view.viewScope
import ru.inteam.touristo.carousel.decorations.CarouselCornersItemDecoration
import ru.inteam.touristo.carousel.decorations.CarouselMarginsItemDecoration
import ru.inteam.touristo.carousel.model.CarouselItem
import ru.inteam.touristo.carousel.model.FlexItem
import ru.inteam.touristo.recycler.RecyclerManager
import ru.inteam.touristo.recycler.clicks.ClickEvent.ItemClick
import ru.inteam.touristo.recycler.item.RecyclerItem
import ru.inteam.touristo.recycler.managerBuilder
import kotlin.math.abs


class CarouselView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr) {
    private val recycler: RecyclerView
    private val image: CarouselFullImageView
    private val recyclerManager: RecyclerManager
    private val snapHelper: LinearSnapHelper
    private val recyclerLayoutParams =
        LayoutParams(MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.carousel_height))

    private val clicks = MutableSharedFlow<ItemClick>(extraBufferCapacity = 10)

    init {
        LayoutInflater.from(context).inflate(R.layout.ui_kit_carousel, this, true)
        image = findViewById(R.id.full_image)

        recycler = findViewById(R.id.recycler)
        recycler.layoutParams = recyclerLayoutParams
        snapHelper = LinearSnapHelper().apply { attachToRecyclerView(recycler) }
        val horizontalLM = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerManager = recycler.managerBuilder()
            .layoutManager(horizontalLM)
            .decorations(CarouselMarginsItemDecoration(), CarouselCornersItemDecoration())
            .build()

        recyclerManager.clicks<CarouselItem>()
            .onEach(::onCarouselItemClicked)
            .launchIn(viewScope)

        image.clicks()
            .onEach { showCarousel() }
            .launchIn(viewScope)

        setWillNotDraw(true)
    }

    private fun onCarouselItemClicked(click: ItemClick) {
        val view = click.view.get() ?: return
        val focusedView = snapHelper.findSnapView(recycler.layoutManager) ?: return
        if (view == focusedView) {
            clicks.tryEmit(click)
            showItem(click.item as CarouselItem)
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
                recycler.getViewByAdapterPosition(i)?.let {
                    recycler.getDecoratedBoundsWithMargins(it, outRect)
                    desiredScroll += outRect.width()
                }
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
        recycler.isVisible = true
    }

    fun submitItems(items: List<CarouselItem>) {
        showCarousel()
        val prevItemCount = recyclerManager.itemCount
        recyclerManager.submitList(addFlexItems(items)) {
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

    private fun addFlexItems(items: List<CarouselItem>): List<RecyclerItem<*, *>> {
        val list = mutableListOf<RecyclerItem<*, *>>(FlexItem(), FlexItem())
        list.addAll(1, items)
        return list
    }

    fun showItem(item: CarouselItem) {
        showFullImage(item)
    }

    private fun showFullImage(item: CarouselItem) {
        recycler.isGone = true
        image.isVisible = true
        image.showFullImage(item)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        measureChild(recycler, widthMeasureSpec, heightMeasureSpec)
        measureChild(image, widthMeasureSpec, heightMeasureSpec)
        val desiredHeight = if (recycler.isVisible) {
            recycler.measuredHeight
        } else {
            image.measuredHeight
        }
        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        recycler.layout(0, 0, width, height)
        image.layout(0, 0, width, height)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    override fun generateLayoutParams(attributeSet: AttributeSet?): LayoutParams {
        return MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }
}
