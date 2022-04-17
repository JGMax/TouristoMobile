package ru.inteam.touristo.common.network.connection.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import ru.inteam.touristo.common.network.connection.NetworkConnectionChecker

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {
    private val networkChecker = NetworkConnectionChecker(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        if (networkChecker.isConnected) {
            return chain.proceed(chain.request())
        } else {
            throw NoNetworkConnectionException()
        }
    }
}
