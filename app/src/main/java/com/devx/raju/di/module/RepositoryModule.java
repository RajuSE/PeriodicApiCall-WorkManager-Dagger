package com.devx.raju.di.module;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.work.WorkManager;

import com.devx.raju.data.local.dao.GithubDao;
import com.devx.raju.data.remote.api.GithubApiService;
import com.devx.raju.data.repository.GithubRepository;
import com.devx.raju.data.remote.api.GithubApiService;
import com.devx.raju.data.repository.GithubRepository;
import com.devx.raju.data.remote.api.GithubApiService;
import com.devx.raju.data.repository.GithubRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    GithubRepository provideGithubRepository(@NonNull GithubDao githubDao, @NonNull GithubApiService githubApiService) {
        return new GithubRepository(githubDao, githubApiService);
    }

}
