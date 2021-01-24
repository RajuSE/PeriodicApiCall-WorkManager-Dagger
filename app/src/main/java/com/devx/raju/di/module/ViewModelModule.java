package com.devx.raju.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devx.raju.di.ViewModelKey;
import com.devx.raju.factory.ViewModelFactory;
import com.devx.raju.ui.viewmodel.GithubListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(GithubListViewModel.class)
    protected abstract ViewModel githubListViewModel(GithubListViewModel githubListViewModel);
}