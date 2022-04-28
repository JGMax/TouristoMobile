package ru.inteam.touristo.ui_kit.shimmer

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class ShimmerLinearLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr), ShimmerView {

    override var anim: Animator? = shimmerAnimator()

    init {
        attachViewToShimmer(this)
        startShimmer()
    }

    override fun onVisibilityChanged(changedView: View, prevVisibility: Int) {
        visibilityChanged(prevVisibility, visibility)
        super.onVisibilityChanged(changedView, visibility)
    }
}
