package com.devx.raju.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.work.*
import com.devx.raju.data.repository.GithubRepository
import com.devx.raju.ui.viewmodel.Constants.SYNC_DATA_WORK_NAME
import com.devx.raju.ui.workers.SyncDataWorker
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
            val periodicSyncDataWork: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
                SyncDataWorker::class.java, 15, TimeUnit.MINUTES)
                    .addTag(TAG_SYNC_DATA)
                    .setConstraints(constraints) // setting a backoff on case the work needs to retry
                    .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                    .build()
            mWorkManager?.enqueueUniquePeriodicWork(
                    SYNC_DATA_WORK_NAME,
                    ExistingPeriodicWorkPolicy.KEEP,  //Existing Periodic Work policy
                    periodicSyncDataWork //work request
            )

//        val downloadWork = OneTimeWorkRequest.Builder(SyncDataWorker::class.java)
//            .addTag(TAG_SYNC_DATA)
//            .keepResultsForAtLeast(0, TimeUnit.SECONDS)
//            .build()
//            Log.i(SyncDataWorker.TAG, "launching work")
//        mWorkManager!!.enqueue(downloadWork)
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