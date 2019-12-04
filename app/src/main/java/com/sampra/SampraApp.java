package com.sampra;

import android.app.Application;

import com.sampra.di.components.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class SampraApp extends DaggerApplication {


    @Override
    public void onCreate() {
        super.onCreate();
    }



    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}
