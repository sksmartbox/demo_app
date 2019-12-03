package com.sampra.ui.home.news.all;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AllFragmentProvider {

    @ContributesAndroidInjector
    abstract AllFragment bindAllFragment();
}
