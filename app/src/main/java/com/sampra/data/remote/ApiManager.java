package com.sampra.data.remote;

import com.sampra.data.model.AllModel;
import com.sampra.ui.home.HomeViewModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiManager {

    @GET("backend/public/api/social_detail")
    Single<AllModel> getAll(@Query("type") String type, @Query("page") String page);

    @GET("backend/public/api/social_detail")
    Single<AllModel> facebook(@Query("type") String type, @Query("page") String page);

    @GET("backend/public/api/social_detail")
    Single<AllModel> twitter(@Query("type") String type, @Query("page") String page);

    @GET("backend/public/api/social_detail")
    Single<AllModel> instagram(@Query("type") String type, @Query("page") String page);
}
