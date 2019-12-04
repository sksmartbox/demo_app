package com.sampra.ui.home.news.all;

import com.sampra.data.model.AllModel;

public interface AllNewsFragementNavigator {


    void handleError(Throwable throwable);

    void response(AllModel allModel);


    void nextResponse(AllModel allViewModel);

    void startAnimation();

    void stopAnimation();
}
