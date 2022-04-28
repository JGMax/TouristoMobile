package ru.inteam.touristo.ui_kit.images

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import ru.inteam.touristo.ui_kit.R
import kotlin.math.min

class SquareImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet,
            intArrayOf(android.R.attr.scaleType)
        )
        val scaleTypeIndex = attrs.getInt(0, 6)
        scaleType = ScaleType.values()[scaleTypeIndex]
        attrs.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        val size = min(width, height)
        setMeasuredDimension(size, size)
    }
}
