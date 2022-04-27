package ru.inteam.touristo.common.network.connection

import android.content.Context
import android.net.ConnectivityManager

class NetworkConnectionChecker(
    context: Context
) {
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnected: Boolean
        get() = connectivityManager.activeNetwork != null

}
