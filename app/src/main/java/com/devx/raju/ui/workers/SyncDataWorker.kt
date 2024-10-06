package com.devx.raju.ui.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.devx.raju.AppController
import com.devx.raju.data.local.entity.GithubEntity
import com.devx.raju.data.repository.GithubRepository
import com.devx.raju.ui.viewmodel.ChildWorkerFactory
import com.devx.raju.ui.viewmodel.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class SyncDataWorker(
    appContext: Context, workerParams: WorkerParameters, val repository: GithubRepository,
) : CoroutineWorker(appContext, workerParams) {

    //runs on worker thread
    override suspend fun doWork(): Result {

        val context = applicationContext
        Log.i(
            TAG,
            "Fetching Data from Remote host " + (repository == null).toString() + getThreadName()
        )

        val sharedPreferences = context.getSharedPreferences("git", Context.MODE_PRIVATE)
        var page = sharedPreferences.getLong("page", 0L)
        page += 1//for simulating diff response since api returns same

        WorkerUtils.makeStatusNotification("Starting fetching for page:" + page, "",context)

        val dataList: List<GithubEntity>? =
            withContext(CoroutineScope(kotlin.coroutines.coroutineContext).coroutineContext) {
                runCatching {
                    val res = repository.getRemoteData(page)
                    Log.i(TAG, "fetching finished" + getThreadName())
                    sharedPreferences.edit().putLong("page", page).commit()
                    res

                }.getOrElse {
                    Log.i(TAG, "fetching Error" + it.message)
                    Log.i(TAG, "is App null " + (AppController.instance == null).toString())
                    Log.i(TAG, "is Repository null " + (repository == null).toString())
                    null
                }
            }

        val result = dataList?.let {
            Log.i(TAG, "fetching SUCCESS" + getThreadName())
            Log.i(TAG, "data size:" + dataList.size)
            Log.i(TAG, "LAST DATA:" +if(dataList.isNotEmpty()) dataList.last() else "emptylist")
            WorkerUtils.makeStatusNotification(
                "New Data for Pg:" + page,
                Constants.NOTIFICATION_TITLE,
                context
            )
            return Result.success()
        } ?: Result.retry()

        Log.i(TAG, "fetching Returned " + result + "_" + getThreadName())

        return result
    }

    companion object {
        val TAG = "DDDWORK"
    }

    fun getThreadName(): String {
        return " : " + Thread.currentThread().name
    }


    class Factory @Inject constructor(
        private val repository: Provider<GithubRepository>,
    ) : ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return SyncDataWorker(
                appContext,
                params,
                repository.get()
            )
        }
    }


}