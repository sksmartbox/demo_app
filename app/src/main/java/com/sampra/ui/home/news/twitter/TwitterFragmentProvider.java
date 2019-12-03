package com.sampra.ui.home.news.twitter;

import com.sampra.ui.home.news.instagram.InstagramFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TwitterFragmentProvider {

    @ContributesAndroidInjector
    abstract TwitterFragment bindTwitterFragment();
}
