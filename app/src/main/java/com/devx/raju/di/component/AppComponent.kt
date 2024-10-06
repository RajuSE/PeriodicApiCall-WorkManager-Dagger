package com.devx.raju.di.component

import android.app.Application
import com.devx.raju.AppController
import com.devx.raju.di.module.ActivityModule
import com.devx.raju.di.module.ApiModule
import com.devx.raju.di.module.DbModule
import com.devx.raju.di.module.RepositoryModule
import com.devx.raju.di.module.ViewModelModule
import com.devx.raju.di.module.WorkModule
import com.devx.raju.di.module.WorkerModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(
    modules = [ApiModule::class, DbModule::class, WorkModule::class, RepositoryModule::class, ViewModelModule::class, ActivityModule::class, WorkerModule::class, AndroidInjectionModule::class]
)
@Singleton
interface AppComponent : AndroidInjector<AppController> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


    override fun inject(appController: AppController)
}
