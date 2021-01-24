package com.devx.raju.di.module;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.work.WorkManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WorkModule {

    @Provides
    @Singleton
    WorkManager provideWorkManager(@NonNull Application application) {
        return    WorkManager.getInstance(application);
    }

}
