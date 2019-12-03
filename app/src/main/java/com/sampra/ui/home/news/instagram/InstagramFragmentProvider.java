package com.sampra.ui.home.news.instagram;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class InstagramFragmentProvider {

    @ContributesAndroidInjector
    abstract InstagramFragment bindInstagramFragment();
}
