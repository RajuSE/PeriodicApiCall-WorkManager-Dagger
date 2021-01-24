package com.devx.raju.di.component;

import android.app.Application;

import com.devx.raju.AppController;
import com.devx.raju.di.module.ActivityModule;
import com.devx.raju.di.module.ApiModule;
import com.devx.raju.di.module.DbModule;
import com.devx.raju.di.module.RepositoryModule;
import com.devx.raju.di.module.ViewModelModule;
import com.devx.raju.di.module.WorkModule;
import com.devx.raju.di.module.WorkerModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


@Component(modules = {
        ApiModule.class,
        DbModule.class,
        WorkModule.class,
        RepositoryModule.class,
        ViewModelModule.class,
        ActivityModule.class,
        WorkerModule.class,
        AndroidSupportInjectionModule.class})
@Singleton
public interface AppComponent {


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }


    void inject(AppController appController);
}
