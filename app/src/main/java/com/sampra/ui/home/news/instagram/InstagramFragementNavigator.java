package com.sampra.ui.home.news.instagram;

import com.sampra.data.model.AllModel;

public interface InstagramFragementNavigator {


    void handleError(Throwable throwable);

    void response(AllModel allModel);


    void nextResponse(AllModel allViewModel);

    void startAnimation();

    void stopAnimation();
}
