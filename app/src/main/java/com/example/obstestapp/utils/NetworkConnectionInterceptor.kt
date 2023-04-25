package com.example.obstestapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NoNetworkException(message: String) : IOException(message)

class NetworkConnectionInterceptor @Inject constructor(private var context: Context) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isNetworkAvailable()) {
            throw NoNetworkException("You have no network access")
        }
        return chain.proceed(chain.request())
    }
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var result = false
    connectivityManager.let {
        it.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> false
                }
            }
    }
    return result
}
