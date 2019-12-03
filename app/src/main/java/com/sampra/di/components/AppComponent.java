package com.sampra.di.components;


import android.app.Application;

import com.sampra.SampraApp;
import com.sampra.di.builders.ActivityBuildersModule;
import com.sampra.di.module.AppModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class,
        AppModule.class
})
public interface AppComponent extends AndroidInjector<SampraApp> {


    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
