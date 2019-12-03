package com.sampra.utils;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sampra.data.DataManager;
import com.sampra.ui.home.HomeViewModel;
import com.sampra.utils.rx.SchedulerProvider;

import javax.inject.Inject;

@SuppressWarnings("unchecked")
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            //noinspection unchecked
            return (T) new HomeViewModel(dataManager,schedulerProvider);
        }

        /*else  if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
            //noinspection unchecked
            return (T) new SignUpViewModel(dataManager,schedulerProvider);
        } else  if (modelClass.isAssignableFrom(SignInViewModel.class)) {
            //noinspection unchecked
            return (T) new SignInViewModel(dataManager,schedulerProvider);
        } else  if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            //noinspection unchecked
            return (T) new HomeViewModel(dataManager,schedulerProvider);
        }  else  if (modelClass.isAssignableFrom(DealsFragmentViewModel.class)) {
            //noinspection unchecked
            return (T) new DealsFragmentViewModel(dataManager,schedulerProvider);
        }  else  if (modelClass.isAssignableFrom(FeedsFragmentViewModel.class)) {
            //noinspection unchecked
            return (T) new FeedsFragmentViewModel(dataManager,schedulerProvider);
        } else  if (modelClass.isAssignableFrom(HomeFragmentViewModel.class)) {
            //noinspection unchecked
            return (T) new HomeFragmentViewModel(dataManager,schedulerProvider);
        }else  if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            //noinspection unchecked
            return (T) new ProfileViewModel(dataManager,schedulerProvider);
        } else  if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashViewModel(dataManager,schedulerProvider);
        }else  if (modelClass.isAssignableFrom(MyShopFragmentViewModel.class)) {
            //noinspection unchecked
            return (T) new MyShopFragmentViewModel(dataManager,schedulerProvider);
        }else  if (modelClass.isAssignableFrom(SoldFragmentViewModel.class)) {
            //noinspection unchecked
            return (T) new SoldFragmentViewModel(dataManager,schedulerProvider);
        }else  if (modelClass.isAssignableFrom(SellingFragmentViewModel.class)) {
            //noinspection unchecked
            return (T) new SellingFragmentViewModel(dataManager,schedulerProvider);
        }*/

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}