package com.sampra.ui.home.news.twitter;

import androidx.databinding.ObservableBoolean;

import com.sampra.data.DataManager;
import com.sampra.data.model.AllModel;
import com.sampra.ui.base.BaseViewModel;
import com.sampra.ui.home.news.facebook.FacebookFragementNavigator;
import com.sampra.utils.rx.SchedulerProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TwitterViewModel extends BaseViewModel<TwitterFragementNavigator> {

    private final ObservableBoolean mIsLoadingShimmer = new ObservableBoolean();

    public TwitterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        getAll("1");
    }

    public void getAll(String page){
        getCompositeDisposable().add(
                getDataManager().twitter("2",page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AllModel>() {
                    @Override
                    public void accept(AllModel allModel) throws Exception {
                        try {
                            if (allModel != null){
                                getNavigator().response(allModel);
                            }
                        }catch (Exception e){
                            getNavigator().handleError(new Throwable(e));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getNavigator().handleError(throwable);
                    }
                })
        );
    }


    public ObservableBoolean getIsLoadingShimmer() {
        return mIsLoadingShimmer;
    }

    public void setIsLoadingShimmer(boolean isLoading) {
        mIsLoadingShimmer.set(isLoading);
    }

    public void getNextAll(String type, String page){
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().twitter(type,page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AllModel>() {
                            @Override
                            public void accept(AllModel allModel) throws Exception {
                                setIsLoading(false);
                                try {
                                    if (allModel != null){
                                        getNavigator().nextResponse(allModel);
                                    }
                                }catch (Exception e){
                                    getNavigator().handleError(new Throwable("Something went worng"));
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                setIsLoading(false);
                                getNavigator().handleError(throwable);
                            }
                        })
        );
    }


    public void clear(){
        onCleared();
    }


}
