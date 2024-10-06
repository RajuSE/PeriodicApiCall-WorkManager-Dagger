package com.devx.raju.data.repository

import android.util.Log
import com.devx.raju.AppConstants
import com.devx.raju.data.local.dao.GithubDao
import com.devx.raju.data.local.entity.GithubEntity
import com.devx.raju.data.remote.api.GithubApiService
import com.devx.raju.data.remote.model.GithubApiResponse
import com.devx.raju.ui.workers.SyncDataWorker

import javax.inject.Singleton

@Singleton
class GithubRepository(private val githubDao: GithubDao, private val githubApiService: GithubApiService) {

    suspend fun getRemoteData(page: Long): List<GithubEntity> {
        val resp = githubApiService.fetchRepositories(AppConstants.QUERY_SORT, AppConstants.QUERY_ORDER, "trending", page)
        Log.i(SyncDataWorker.TAG, "Saving in DB.."+Thread.currentThread().name)
        saveApiDataToDb(resp, page)
        return resp.items!!
    }

    fun getLocalData() = githubDao.getRepositories()

    private fun saveApiDataToDb(item: GithubApiResponse, page: Long) {
        var list = item.items
        for (githubEntity in list!!) {
            githubEntity.page = page
            githubEntity.totalPages = item.totalCount
        }
        githubDao.insertRepositories(list)
    }


}