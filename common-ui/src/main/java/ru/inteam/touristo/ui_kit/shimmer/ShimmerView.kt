package ru.inteam.touristo.ui_kit.shimmer

import android.animation.Animator
import android.view.View
import android.view.View.VISIBLE
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.doOnDetach

interface ShimmerView {
    var anim: Animator?

    fun attachViewToShimmer(view: View) {
        view.doOnDetach {
            stopShimmer()
            anim = null
        }
    }

    fun visibilityChanged(prevVisibility: Int, currentVisibility: Int) {
        if (currentVisibility == VISIBLE && prevVisibility == VISIBLE) {
            startShimmer()
        } else {
            stopShimmer()
        }
    }

    fun startShimmer() {
        if (anim?.isStarted == true) return
        anim?.start()
    }

    fun stopShimmer() {
        anim?.cancel()
    }
}
