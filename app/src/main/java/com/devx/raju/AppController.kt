package com.devx.raju

import android.app.Activity
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.devx.raju.data.local.AppDatabase
import com.devx.raju.di.component.DaggerAppComponent
import com.devx.raju.ui.viewmodel.DaggerWorkerFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class AppController : Application(), HasActivityInjector {
    @JvmField
    @Inject
    var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null
    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector!!
    }

    @Inject lateinit var workerFactory: DaggerWorkerFactory


    override fun onCreate() {
        super.onCreate()
        instance = this
        DaggerAppComponent.builder()
                .application(this)!!
                .build()!!
                .inject(this)

        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())

    }

    companion object {
        var instance: AppController? = null
    }
}