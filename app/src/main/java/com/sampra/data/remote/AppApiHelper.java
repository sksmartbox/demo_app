/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.sampra.data.remote;


import com.sampra.data.model.AllModel;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;


public class AppApiHelper implements ApiHelper {

    private final ApiManager apiManager;

    @Inject
    public AppApiHelper(ApiManager apiManager) {
        this.apiManager = apiManager;
    }


    @Override
    public Single<AllModel> getAll(String type, String page) {
        return apiManager.getAll(type, page);
    }

    @Override
    public Single<AllModel> facebook(String type, String page) {
        return null;
    }

    @Override
    public Single<AllModel> twitter(String type, String page) {
        return null;
    }

    @Override
    public Single<AllModel> instagram(String type, String page) {
        return null;
    }


}
