package com.devx.raju.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.devx.raju.data.local.entity.GithubEntity
import com.devx.raju.data.repository.GithubRepository
import com.devx.raju.ui.viewmodel.Constants.SYNC_DATA_WORK_NAME
import com.devx.raju.ui.workers.SyncDataWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class GithubListViewModel @Inject constructor(private val githubRepository: GithubRepository, private val mWorkManager: WorkManager) : ViewModel(){

    val TAG_SYNC_DATA = "TAG_SYNC_DATA"

    fun fetchRepositories() {
        viewModelScope.launch(Dispatchers.IO) {
            val constraints: Constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val periodicSyncDataWork: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
                SyncDataWorker::class.java, 15, TimeUnit.MINUTES
            )
                .addTag(TAG_SYNC_DATA)
                .setConstraints(constraints) // setting a backoff on case the work needs to retry
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
            mWorkManager.enqueueUniquePeriodicWork(
                SYNC_DATA_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,  //Existing Periodic Work policy
                periodicSyncDataWork //work request
            )
            Log.i(SyncDataWorker.TAG, "launching work1")

//            val onetimeTask = OneTimeWorkRequest.Builder(SyncDataWorker::class.java)
//                .addTag(TAG_SYNC_DATA)
//                .keepResultsForAtLeast(0, TimeUnit.SECONDS)
//                .build()
//            mWorkManager.enqueue(onetimeTask)

//TESTING FLOW behaviour with Lifecycle and multiple onetime tasks

           /* delay(30000)

            val onetimeTask2 = OneTimeWorkRequest.Builder(SyncDataWorker::class.java)
                .addTag(TAG_SYNC_DATA)
                .keepResultsForAtLeast(0, TimeUnit.SECONDS)
                .build()
            mWorkManager.enqueue(onetimeTask2)

            Log.i(SyncDataWorker.TAG, "launching work2")

            delay(30000)

            val onetimeTask3 = OneTimeWorkRequest.Builder(SyncDataWorker::class.java)
                .addTag(TAG_SYNC_DATA)
                .keepResultsForAtLeast(0, TimeUnit.SECONDS)
                .build()
            mWorkManager.enqueue(onetimeTask3)

            Log.i(SyncDataWorker.TAG, "launching work3")*/
        }

    }

    val _stateflow = MutableStateFlow<List<GithubEntity>>(emptyList())
    val stateFlow:Flow<List<GithubEntity>> = _stateflow.asStateFlow()

    fun loadLocalData() {
        viewModelScope.launch {
            githubRepository.getLocalData().collectLatest {
                _stateflow.value = it
            }
        }
    }

    fun getOutputWorkInfo(): Flow<List<WorkInfo>>? {
        return mSavedWorkInfo
    }

    private var mSavedWorkInfo: Flow<List<WorkInfo>>? = null

    init {
        mSavedWorkInfo = mWorkManager.getWorkInfosByTagFlow(TAG_SYNC_DATA)
        fetchRepositories()
    }
}