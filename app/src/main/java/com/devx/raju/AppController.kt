package com.devx.raju

import android.app.Activity
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.devx.raju.data.local.AppDatabase
import com.devx.raju.di.component.DaggerAppComponent
import com.devx.raju.ui.viewmodel.DaggerWorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
//import dagger.android.HasActivityInjector
import javax.inject.Inject

class AppController : DaggerApplication() {
//    @Inject
//    lateinit var dispatchingAndroidInjector: AndroidInjector<Activity>
//    override fun androidInjector(): AndroidInjector<Activity> {
//        return dispatchingAndroidInjector!!
//    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)!!
            .build()!!
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