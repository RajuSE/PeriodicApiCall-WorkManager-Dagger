package com.devx.raju.data.repository

import com.devx.raju.AppConstants
import com.devx.raju.data.local.dao.GithubDao
import com.devx.raju.data.local.entity.GithubEntity
import com.devx.raju.data.remote.api.GithubApiService
import com.devx.raju.data.remote.model.GithubApiResponse

import javax.inject.Singleton

@Singleton
class GithubRepository(private val githubDao: GithubDao, private val githubApiService: GithubApiService) {

    suspend fun getRemoteData(page: Long): List<GithubEntity> {
        val resp = githubApiService.fetchRepositories(AppConstants.QUERY_SORT, AppConstants.QUERY_ORDER, page)
        saveApiDataToDb(resp, page)
        return resp.items
    }

    fun getLocalData() = githubDao.getRepositories()

    fun saveApiDataToDb(item: GithubApiResponse, page: Long) {
        var list = item.items
        for (githubEntity in list) {
            githubEntity.page = page
            githubEntity.totalPages = item.totalCount
        }
        githubDao.insertRepositories(list)
    }


}