package com.devx.raju.di.module;

import com.devx.raju.ui.activity.GithubListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract GithubListActivity contributeGithubListActivity();
}