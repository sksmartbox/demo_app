package com.sampra.di.builders;

import com.sampra.ui.home.HomeActivity;
import com.sampra.ui.home.news.NewsFragmentProvider;
import com.sampra.ui.home.news.all.AllFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {


    @ContributesAndroidInjector(modules = {
            NewsFragmentProvider.class,
            AllFragmentProvider.class
    })
    abstract HomeActivity bindHomeActivity();
}
