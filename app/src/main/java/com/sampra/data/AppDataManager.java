package com.sampra.data;


import android.content.Context;

import com.google.gson.Gson;
import com.sampra.data.local.prefs.PreferencesHelper;
import com.sampra.data.model.AllModel;
import com.sampra.data.remote.ApiHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

public class AppDataManager implements DataManager {
    private final Context context;
    private final ApiHelper apiHelper;
    private final PreferencesHelper preferencesHelper;
    private final Gson gson;

    @Inject
    public AppDataManager(Context context, ApiHelper apiHelper,PreferencesHelper preferencesHelper, Gson gson){
        this.context = context;
        this.apiHelper = apiHelper;
        this.preferencesHelper = preferencesHelper;
        this.gson = gson;
    }

    @Override
    public Single<AllModel> getAll(String type, String page) {
        return apiHelper.getAll(type,page);
    }

    @Override
    public Single<AllModel> facebook(String type, String page) {
        return apiHelper.facebook(type,page);
    }

    @Override
    public Single<AllModel> twitter(String type, String page) {
        return apiHelper.twitter(type,page);
    }

    @Override
    public Single<AllModel> instagram(String type, String page) {
        return apiHelper.instagram(type,page);
    }
}
