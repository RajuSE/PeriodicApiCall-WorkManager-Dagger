package com.devx.raju.di.module

import com.devx.raju.data.local.dao.GithubDao
import com.devx.raju.data.remote.api.GithubApiService
import com.devx.raju.data.repository.GithubRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideGithubRepository(githubDao: GithubDao, githubApiService: GithubApiService): GithubRepository {
        return GithubRepository(githubDao, githubApiService)
    }
}