package com.sampra.ui.home.news.instagram;


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
import com.sampra.databinding.FragmentInstsgramBinding;
import com.sampra.ui.base.BaseFragment;
import com.sampra.ui.home.news.facebook.FacebookViewModel;
import com.sampra.utils.ViewModelProviderFactory;

import javax.inject.Inject;

public class InstagramFragment extends BaseFragment<FragmentInstsgramBinding, InstagramViewModel> implements InstagramFragementNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private InstagramViewModel viewModel;

    FragmentInstsgramBinding binding;

    public static InstagramFragment newInstance(String param1, String param2) {
        InstagramFragment fragment = new InstagramFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return com.sampra.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_instsgram;
    }

    @Override
    public InstagramViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(InstagramViewModel.class);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instsgram, container, false);
    }*/

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = getViewDataBinding();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void response(AllModel allModel) {

    }

    @Override
    public void onDestroyView() {
        viewModel.clear();
        super.onDestroyView();
    }
}
