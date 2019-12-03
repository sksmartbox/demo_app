package com.sampra.ui.home.news.facebook;

import com.sampra.data.model.AllModel;

public interface FacebookFragementNavigator {


    void handleError(Throwable throwable);

    void response(AllModel allModel);
}
