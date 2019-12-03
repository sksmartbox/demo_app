package com.sampra.di.builders;

import com.sampra.ui.home.HomeActivity;
import com.sampra.ui.home.news.NewsFragmentProvider;
import com.sampra.ui.home.news.all.AllFragmentProvider;
import com.sampra.ui.home.news.facebook.FacebookFragmentProvider;
import com.sampra.ui.home.news.instagram.InstagramFragmentProvider;
import com.sampra.ui.home.news.twitter.TwitterFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {


    @ContributesAndroidInjector(modules = {
            NewsFragmentProvider.class,
            AllFragmentProvider.class,
            FacebookFragmentProvider.class,
            TwitterFragmentProvider.class,
            InstagramFragmentProvider.class
    })
    abstract HomeActivity bindHomeActivity();
}
