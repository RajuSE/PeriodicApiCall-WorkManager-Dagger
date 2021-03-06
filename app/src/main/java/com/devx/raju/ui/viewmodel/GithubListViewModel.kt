package com.devx.raju.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.devx.raju.data.local.entity.GithubEntity
import com.devx.raju.data.repository.GithubRepository
import com.devx.raju.ui.viewmodel.Constants.SYNC_DATA_WORK_NAME
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class GithubListViewModel @Inject constructor(githubRepository: GithubRepository, work: WorkManager) : BaseViewModel() {
    private val repository: GithubRepository

    var mWorkManager: WorkManager? = null

    val TAG_SYNC_DATA = "TAG_SYNC_DATA"


    fun fetchRepositories2() {
            val constraints: Constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            val periodicSyncDataWork: PeriodicWorkRequest = PeriodicWorkRequest.Builder(SyncDataWorker::class.java, 15, TimeUnit.MINUTES)
                    .addTag(TAG_SYNC_DATA)
                    .setConstraints(constraints) // setting a backoff on case the work needs to retry
                    .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                    .build()
            mWorkManager?.enqueueUniquePeriodicWork(
                    SYNC_DATA_WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,  //Existing Periodic Work policy
                    periodicSyncDataWork //work request
            )
            Log.i(SyncDataWorker.TAG, "launching work")

        /*if (false) {
            repositoryListLiveData = repository.getLocalData(currentPage)
            return
        }*/

        /*launchViewModelScope {

            val d = withContext(CoroutineScope(coroutineContext).coroutineContext) {
                runCatching { repository.getRemoteData(currentPage) }
                        .getOrElse {
                            onError(it)
                            repositoryListLiveData = repository.getLocalData(currentPage)
                            null
                        }
            }
            d?.let {
                _repositoryListLiveData.postValue(d)
            }
        }*/

    }

    fun loadLocalData()  = repository.getLocalData()


    fun getOutputWorkInfo(): LiveData<List<WorkInfo>>? {
        return mSavedWorkInfo
    }

    private var mSavedWorkInfo: LiveData<List<WorkInfo>>? = null

    init {
        repository = githubRepository//GithubRepository(githubDao!!, githubApiService!!)
//        AppController.instance.repository = githubRepository
        mWorkManager = work
        mSavedWorkInfo = mWorkManager!!.getWorkInfosByTagLiveData(TAG_SYNC_DATA)

    }
}