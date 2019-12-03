package com.sampra.ui.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.sampra.data.DataManager;
import com.sampra.utils.rx.SchedulerProvider;

public class AllDataSourceFactory extends DataSource.Factory {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;
    ResponseDataSource photoDataSource;
    MutableLiveData<ResponseDataSource> mutableLiveData;

    public AllDataSourceFactory(DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        photoDataSource = new ResponseDataSource(dataManager, schedulerProvider);
        mutableLiveData.postValue(photoDataSource);
        return photoDataSource;
    }

    public MutableLiveData<ResponseDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
