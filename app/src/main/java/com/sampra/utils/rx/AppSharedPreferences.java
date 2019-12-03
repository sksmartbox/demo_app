package com.sampra.utils.rx;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {

    private static AppSharedPreferences instance;

    private AppSharedPreferences(){

    }
    public static AppSharedPreferences newInstance() {
        if(instance == null){
            instance = new AppSharedPreferences();
            return instance;
        }
        return instance;
    }

    public void setImagePath(Context context, String currentPhotoPath) {
        SharedPreferences.Editor editor =   context.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(AppConstants.FILE_IMAGE,currentPhotoPath);
        editor.apply();
    }

    public String getImagePath(Context context){
        SharedPreferences sharedPreferences =   context.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppConstants.FILE_IMAGE,null);
    }

    public String getHeaderToken(Context context) {
        SharedPreferences sharedPreferences =   context.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppConstants.TOKEN,null);
    }

    public void setHeaderToken(Context context, String token) {
        SharedPreferences.Editor editor =   context.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(AppConstants.TOKEN,token);
        editor.apply();
    }
}
