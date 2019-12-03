package com.sampra.ui.home.news.all;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sampra.BR;
import com.sampra.R;
import com.sampra.data.model.AllModel;
import com.sampra.databinding.FragmentAllBinding;
import com.sampra.ui.base.BaseFragment;
import com.sampra.utils.ViewModelProviderFactory;

import javax.inject.Inject;


public class AllFragment extends BaseFragment<FragmentAllBinding,AllViewModel> implements AllNewsFragementNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private AllViewModel viewModel;

    private FragmentAllBinding binding;

    public AllFragment() {
        // Required empty public constructor
    }


    public static AllFragment newInstance(String param1, String param2) {
        AllFragment fragment = new AllFragment();
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all;
    }

    @Override
    public AllViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(AllViewModel.class);
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
        return inflater.inflate(R.layout.fragment_all, container, false);
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
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
    }
}
