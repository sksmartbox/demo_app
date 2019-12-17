package com.sampra.ui.home.news.twitter;

import com.sampra.data.model.AllModel;

public interface TwitterFragementNavigator {


    void handleError(Throwable throwable);

    void response(AllModel allModel);

    void responseNext(AllModel allModel);
}
