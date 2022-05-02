package ru.inteam.touristo.common.ui.network

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import ru.inteam.touristo.common.network.connection.NetworkConnectionDetector

abstract class NetworkAppCompatActivity(@LayoutRes resId: Int) : AppCompatActivity(resId) {

    private val connectivityDetector by lazy {
        NetworkConnectionDetector(this)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            connectivityDetector.isConnectedFlow
                .onEach(::onConnectionChanged)
                .collect()
        }
    }

    override fun onStart() {
        super.onStart()
        connectivityDetector.startDetection()
    }

    override fun onStop() {
        super.onStop()
        connectivityDetector.stopDetection()
    }

    open fun onConnectionChanged(isConnected: Boolean) = Unit
}
