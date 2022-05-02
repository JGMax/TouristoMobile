package ru.inteam.touristo.common_ui.images

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

class SquareImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {

    init {
        val scaleTypeAttrs = context.obtainStyledAttributes(
            attributeSet,
            intArrayOf(android.R.attr.scaleType)
        )
        val scaleTypeIndex = scaleTypeAttrs.getInt(0, 6)
        scaleType = ScaleType.values()[scaleTypeIndex]
        scaleTypeAttrs.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        val size = min(width, height)
        setMeasuredDimension(size, size)
    }
}
