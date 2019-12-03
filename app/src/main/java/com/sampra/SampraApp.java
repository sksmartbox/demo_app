package com.sampra;

import android.app.Application;

import com.sampra.di.components.DaggerAppComponent;

public class SampraApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }
}
