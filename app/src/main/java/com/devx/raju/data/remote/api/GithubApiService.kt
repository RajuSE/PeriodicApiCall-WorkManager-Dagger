package com.devx.raju.data.remote.api

import com.devx.raju.data.remote.model.GithubApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    suspend fun fetchRepositories(@Query("sort") sort: String,
                           @Query("order") order: String,
                           @Query("page") page: Long): GithubApiResponse

}