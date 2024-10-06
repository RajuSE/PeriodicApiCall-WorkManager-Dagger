package com.devx.raju.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object InternetUtil {
     fun isConnectionOn(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

         val network = connectivityManager.activeNetwork
         val connection = connectivityManager.getNetworkCapabilities(network)
         return connection != null && (
                 connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                         connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
     }
}