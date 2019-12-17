package com.sampra.ui.home.news.instagram;

import com.sampra.data.DataManager;
import com.sampra.data.model.AllModel;
import com.sampra.ui.base.BaseViewModel;
import com.sampra.ui.home.news.facebook.FacebookFragementNavigator;
import com.sampra.utils.rx.SchedulerProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class InstagramViewModel extends BaseViewModel<InstagramFragementNavigator> {

    public InstagramViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        getAll("initial","1");
    }

    public void getAll(String initialization, String page){
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().getAll("3",page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AllModel>() {
                    @Override
                    public void accept(AllModel allModel) throws Exception {
                        setIsLoading(false);
                        if (initialization.equalsIgnoreCase("initial")){
                            try {
                                if (allModel != null){
                                    getNavigator().response(allModel);
                                }
                            }catch (Exception e){
                                getNavigator().handleError(new Throwable("Something went worng"));
                            }
                        } else {
                            try {
                                if (allModel != null){
                                    getNavigator().responseNext(allModel);
                                }
                            }catch (Exception e){
//                                getNavigator().handleError(new Throwable("Something went worng"));
                            }
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
