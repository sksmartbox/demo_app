package com.sampra.data;


import android.content.Context;

import com.google.gson.Gson;
import com.sampra.data.local.prefs.PreferencesHelper;
import com.sampra.data.remote.ApiHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDataManager implements DataManager{

    @Inject
    public AppDataManager(Context context, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson){

    }

}
