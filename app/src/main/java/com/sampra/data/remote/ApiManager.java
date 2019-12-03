package com.sampra.data.remote;

import com.sampra.data.model.AllModel;
import com.sampra.ui.home.HomeViewModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiManager {


    @GET("social_detail")
    Single<AllModel> getAll(@Query("type") String type, @Query("page") String page);
}
