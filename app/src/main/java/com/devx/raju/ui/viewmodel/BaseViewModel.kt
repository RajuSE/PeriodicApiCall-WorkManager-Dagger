package com.devx.raju.ui.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devx.raju.data.remote.interceptor.RequestInterceptor
import com.devx.raju.utils.ToastUtil.Companion.showShort
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.UnknownHostException
import kotlin.coroutines.coroutineContext

abstract class BaseViewModel() : ViewModel() {

    open fun launchViewModelScope(doWork: suspend () -> Unit): Job {
        return viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            doWork()
        }
    }

    open val isLoading = MutableLiveData(View.GONE)
    open fun showLoading() = isLoading.postValue(View.VISIBLE)
    open fun hideLoading() = isLoading.postValue(View.GONE)

    open val isNoData = MutableLiveData(View.GONE)
    open fun showNoData() = isNoData.postValue(View.VISIBLE)
    open fun hideNoData() = isNoData.postValue(View.GONE)

    open val showRetry = MutableLiveData(View.GONE)
    open fun showRetryBtn() = showRetry.postValue(View.VISIBLE)
    open fun hideRetryBtn() = showRetry.postValue(View.GONE)

    open fun onError(t: Throwable) {
        viewModelScope.launch {
//            when (t) {
//                is RequestInterceptor.NoConnectivityException ->
//                    showShort("No internet", )
//                is HttpException -> showShort("Unable to connect to server", app.applicationContext)
//                is UnknownHostException -> {
//                    showShort("Unable to connect to server. Please check your internet connection.",app.applicationContext)
//                }
//                else -> {
//                    showShort("Something went wrong",app.applicationContext)
//                    Log.e("ERROR", "${t.message}")
//                }
//            }
            hideLoading()
        }
    }

    suspend fun <T> handle(call: suspend () -> T): T? {
        return withContext(CoroutineScope(coroutineContext).coroutineContext) {
            call.runCatching { this.invoke() }
                .getOrElse {
                    onError(it)
                    null
                }
        }
    }

}