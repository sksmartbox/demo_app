package com.sampra.ui.home.news;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NewsFragmentProvider {

    @ContributesAndroidInjector
    abstract NewsFragment bindNewsFragment();
}
