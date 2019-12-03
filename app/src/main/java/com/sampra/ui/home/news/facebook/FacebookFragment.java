package com.sampra.ui.home.news.facebook;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sampra.BR;
import com.sampra.R;
import com.sampra.data.model.AllModel;
import com.sampra.databinding.FragmentAllBinding;
import com.sampra.databinding.FragmentFacebookBinding;
import com.sampra.ui.base.BaseFragment;
import com.sampra.ui.home.news.all.AllViewModel;
import com.sampra.utils.ViewModelProviderFactory;

import javax.inject.Inject;


public class FacebookFragment extends BaseFragment<FragmentFacebookBinding, FacebookViewModel> implements FacebookFragementNavigator {


    @Inject
    ViewModelProviderFactory factory;
    private FacebookViewModel viewModel;

    private FragmentFacebookBinding binding;


    public static FacebookFragment newInstance(String param1, String param2) {
        FacebookFragment fragment = new FacebookFragment();
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return com.sampra.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_facebook;
    }

    @Override
    public FacebookViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(FacebookViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);

    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_facebook, container, false);
    }*/

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding =getViewDataBinding();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void response(AllModel allModel) {

    }
}
