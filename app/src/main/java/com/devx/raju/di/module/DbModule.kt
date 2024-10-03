package com.devx.raju.di.module

import android.app.Application
import androidx.room.Room
import com.devx.raju.data.local.AppDatabase
import com.devx.raju.data.local.dao.GithubDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application,
                AppDatabase::class.java, "Github.db")
            .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideGithubDao(appDatabase: AppDatabase): GithubDao {
        return appDatabase.githubDao()
    }
}