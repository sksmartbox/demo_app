package com.sampra.di.module;


import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.sampra.BuildConfig;
import com.sampra.data.AppDataManager;
import com.sampra.data.DataManager;
import com.sampra.data.local.prefs.AppPreferencesHelper;
import com.sampra.data.local.prefs.PreferencesHelper;
import com.sampra.data.remote.ApiHelper;
import com.sampra.data.remote.ApiManager;
import com.sampra.data.remote.AppApiHelper;
import com.sampra.di.scope.PreferenceInfo;
import com.sampra.utils.rx.AppConstants;
import com.sampra.utils.rx.AppSchedulerProvider;
import com.sampra.utils.rx.SchedulerProvider;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
//                .setLongSerializationPolicy( LongSerializationPolicy.STRING )
//                .setDateFormat(DateFormat.LONG)
//                .registerTypeAdapter(Date.class, new JsonDateDeserializer())
//                .registerTypeAdapter(Date.class, serializer)
//                .registerTypeAdapter(Date.class, deserializer)
//                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }


    @Provides
    OkHttpClient providerOkHttpClient(PreferencesHelper preferencesHelper){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        if(preferencesHelper.getAccessToken()  != null){
                            Request newRequest  = chain.request().newBuilder()
//                                    .addHeader("Authorization", "Bearer " + token)
                                    .addHeader("accessToken", preferencesHelper.getAccessToken())
                                    .build();
                            return chain.proceed(newRequest);
                        }
                        Request newRequest  = chain.request().newBuilder()
                                .build();
                        return chain.proceed(newRequest);
                    }
                }).build();
        return client;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    ApiManager provideApiManager(OkHttpClient client, Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ApiManager.class);
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }


    @Provides
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }
}
