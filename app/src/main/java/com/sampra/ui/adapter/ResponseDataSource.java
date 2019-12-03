package com.sampra.ui.adapter;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.sampra.data.DataManager;
import com.sampra.data.model.AllModel;
import com.sampra.utils.rx.SchedulerProvider;

public class ResponseDataSource extends PageKeyedDataSource<Long, AllModel> {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    public ResponseDataSource(DataManager dataManager, SchedulerProvider schedulerProvider){
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, AllModel> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, AllModel> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, AllModel> callback) {

    }
}
