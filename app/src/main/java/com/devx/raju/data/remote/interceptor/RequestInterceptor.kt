package com.devx.raju.data.remote.interceptor

import android.content.Context
import android.util.Log
import com.devx.raju.AppConstants
import com.devx.raju.utils.InternetUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RequestInterceptor constructor(
        private val context: Context
) : Interceptor {
    val tag = "RequestInterceptor::"


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!InternetUtil.isConnectionOn(context)) {
            Log.i(tag, "noconnect")
            throw NoConnectivityException()
        }
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val url = originalUrl.newBuilder()
                .addQueryParameter("q", AppConstants.QUERY_API)
                .addQueryParameter("per_page", AppConstants.PAGE_MAX_SIZE)
                .build()
        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    class NoConnectivityException : IOException() {
        override val message: String
            get() = "NoConnectivityException"
    }
}