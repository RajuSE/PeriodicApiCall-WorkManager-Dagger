package com.devx.raju.di.module

import com.devx.raju.ui.viewmodel.ChildWorkerFactory
import com.devx.raju.ui.viewmodel.SyncDataWorker
import com.devx.raju.ui.viewmodel.WorkerKey
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(SyncDataWorker::class)
    fun bindSyncDataWorker(factory: SyncDataWorker.Factory): ChildWorkerFactory
}


@Component(
        modules = [
            WorkerModule::class
        ]
)
interface SampleComponent {
    // other method
}