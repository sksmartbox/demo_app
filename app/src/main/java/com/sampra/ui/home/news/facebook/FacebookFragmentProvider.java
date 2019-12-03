package com.sampra.ui.home.news.facebook;

import com.sampra.ui.home.news.all.AllFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FacebookFragmentProvider {

    @ContributesAndroidInjector
    abstract FacebookFragment bindFacebookFragment();
}
