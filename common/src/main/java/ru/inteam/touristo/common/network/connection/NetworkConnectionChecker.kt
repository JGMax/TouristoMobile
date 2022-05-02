package ru.inteam.touristo.common.network.connection

import android.content.Context
import android.net.ConnectivityManager
import ru.inteam.touristo.common.util.systemService

class NetworkConnectionChecker(
    context: Context
) {
    private val connectivityManager: ConnectivityManager = context.systemService()

    val isConnected: Boolean
        get() = connectivityManager.activeNetwork != null

}
