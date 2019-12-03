package com.sampra.ui.home.news.twitter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sampra.BR;
import com.sampra.R;
import com.sampra.data.model.AllModel;
import com.sampra.databinding.FragmentInstsgramBinding;
import com.sampra.databinding.FragmentTwitterBinding;
import com.sampra.ui.base.BaseFragment;
import com.sampra.ui.home.news.instagram.InstagramViewModel;
import com.sampra.utils.ViewModelProviderFactory;

import javax.inject.Inject;


public class TwitterFragment extends BaseFragment<FragmentTwitterBinding, TwitterViewModel> implements TwitterFragementNavigator {


    @Inject
    ViewModelProviderFactory factory;
    private TwitterViewModel viewModel;
    FragmentTwitterBinding binding;


    public static TwitterFragment newInstance(String param1, String param2) {
        TwitterFragment fragment = new TwitterFragment();
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return com.sampra.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_twitter;
    }

    @Override
    public TwitterViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(TwitterViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_twitter, container, false);
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void response(AllModel allModel) {

    }
}