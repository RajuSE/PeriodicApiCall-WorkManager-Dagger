package com.devx.raju.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.devx.raju.AppController
import com.devx.raju.data.repository.GithubRepository
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Provider

class SyncDataWorker(appContext: Context, workerParams: WorkerParameters, val repository: GithubRepository
) : Worker(appContext, workerParams) {

    companion object {
        val TAG = "DDD"
    }

    override fun doWork(): Result = runBlocking {

        val applicationContext = applicationContext
        Log.i(TAG, "Fetching Data from Remote host " + (repository == null).toString())


        val sharedPreferences = applicationContext.getSharedPreferences("git", Context.MODE_PRIVATE)
        var page = sharedPreferences.getLong("page", 0L)
        page = page+ 1//for simulating diff response since api returns same
        WorkerUtils.makeStatusNotification("Starting fetching for page:"+page, "", applicationContext);
        try {

            val job = GlobalScope.async {
                val d = withContext(CoroutineScope(kotlin.coroutines.coroutineContext).coroutineContext) {
                    runCatching {
                        repository.getRemoteData(page)
                        Log.i(TAG, "fetching finished")
                        sharedPreferences.edit().putLong("page", page).commit()


                    }
                            .getOrElse {
//                            println(repositoryListLiveData)
                                Log.i(TAG, "fetching Error" + it.message)
                                Log.i(TAG, "is App null " + (AppController.instance == null).toString())
                                Log.i(TAG, "is Repository null " + (repository == null).toString())
                                null
                            }
                }

                val let: Result = d?.let {
                    Log.i(TAG, "fetching SUCCESS")
                    WorkerUtils.makeStatusNotification("New Data Available Pg:"+page, Constants.NOTIFICATION_TITLE, applicationContext);
                    return@let Result.success();
                }
                        ?: Result.retry()

                return@async let
            }

            var result = job.await()!!
            Log.i(TAG, "fetching Returned " + result)
            return@runBlocking result
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i(TAG, "fetching Exception" + e.message)
            return@runBlocking Result.failure()
        }
    }

    override fun onStopped() {
        super.onStopped()
        Log.i(TAG, "OnStopped called for this worker")
    }


    class Factory @Inject constructor(
            private val repository: Provider<GithubRepository>
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