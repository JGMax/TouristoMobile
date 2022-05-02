package ru.inteam.touristo.common_ui.shimmer

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.CycleInterpolator

private const val MIN_SHIMMER_FADE = 0.4f
private const val MAX_SHIMMER_FADE = 1.0f
private const val SHIMMER_DURATION = 1000L

fun View.shimmerAnimator(): ValueAnimator {
    return ObjectAnimator.ofFloat(
        this,
        "alpha",
        (MIN_SHIMMER_FADE + MAX_SHIMMER_FADE) / 2,
        MAX_SHIMMER_FADE
    ).apply {
        duration = SHIMMER_DURATION
        interpolator = CycleInterpolator(1f)
        repeatMode = ValueAnimator.RESTART
        repeatCount = ValueAnimator.INFINITE
        start()
    }
}
