package com.sampra.ui.home;

import com.sampra.data.DataManager;
import com.sampra.ui.base.BaseViewModel;
import com.sampra.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
