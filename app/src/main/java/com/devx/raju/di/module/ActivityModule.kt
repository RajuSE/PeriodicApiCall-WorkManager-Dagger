package com.devx.raju.di.module

import com.devx.raju.ui.activity.GithubListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeGithubListActivity(): GithubListActivity?
}