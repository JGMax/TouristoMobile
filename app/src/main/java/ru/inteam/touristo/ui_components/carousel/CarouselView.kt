package ru.inteam.touristo.ui_components.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import ru.inteam.touristo.R
import ru.inteam.touristo.common.ui.recycler.RecyclerManager
import ru.inteam.touristo.common.ui.recycler.managerBuilder
import ru.inteam.touristo.common.ui.view.getViewLayoutParamsByMaxWidth
import ru.inteam.touristo.common.util.displayWidth
import ru.inteam.touristo.ui_components.carousel.decorations.CarouselCornersItemDecoration
import ru.inteam.touristo.ui_components.carousel.decorations.CarouselMarginsItemDecoration
import ru.inteam.touristo.ui_components.carousel.model.CarouselItem

class CarouselView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr) {
    private val recycler: RecyclerView
    private val image: ImageView
    private val recyclerManager: RecyclerManager
    private val snapHelper: SnapHelper
    private val recyclerLayoutParams =
        LayoutParams(MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.carousel_height))

    init {
        LayoutInflater.from(context).inflate(R.layout.ui_component_carousel, this, true)
        recycler = findViewById(R.id.recycler)
        recycler.layoutParams = recyclerLayoutParams
        snapHelper = LinearSnapHelper().apply { attachToRecyclerView(recycler) }
        image = findViewById(R.id.image)
        val horizontalLM = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerManager = recycler.managerBuilder()
            .layoutManager(horizontalLM)
            .decorations(CarouselMarginsItemDecoration(), CarouselCornersItemDecoration())
            .build()
    }

    fun submitItems(items: List<CarouselItem>) {
        recycler.isVisible = true
        image.isGone = true
        val prevItemCount = recyclerManager.itemCount
        recyclerManager.submitList(items) {
            if (prevItemCount != 0 && recyclerManager.itemCount != 0) {
                recycler.scrollToPosition(recyclerManager.itemCount - 1)
            } else {
                recycler.scrollToPosition(0)
            }
        }
        requestLayout()
    }

    fun showItem(item: CarouselItem) {
        recycler.isGone = true
        image.isVisible = true
        val bitmap = item.source()
        image.layoutParams = bitmap.getViewLayoutParamsByMaxWidth(context.displayWidth)
        image.setImageBitmap(bitmap)
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

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(MATCH_PARENT, MATCH_PARENT)
    }

    override fun generateLayoutParams(attributeSet: AttributeSet?): LayoutParams {
        return MarginLayoutParams(MATCH_PARENT, MATCH_PARENT)
    }
}
