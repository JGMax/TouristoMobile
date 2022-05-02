package ru.inteam.touristo.common.ui.network

import android.content.Context
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import ru.inteam.touristo.common.network.connection.NetworkConnectionDetector

abstract class NetworkFragment(@LayoutRes resId: Int) : Fragment(resId) {

    private val connectivityDetector by lazy {
        NetworkConnectionDetector(requireContext())
    }

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
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
