package com.sampra.ui.home.news.all;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.sampra.BR;
import com.sampra.R;
import com.sampra.data.model.AllModel;
import com.sampra.data.model.RecordsItem;
import com.sampra.databinding.FragmentAllBinding;
import com.sampra.ui.adapter.AllAdapter;
import com.sampra.ui.adapter.EndlessRecyclerViewScrollListener;
import com.sampra.ui.base.BaseFragment;
import com.sampra.utils.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;


public class AllFragment extends BaseFragment<FragmentAllBinding,AllViewModel> implements AllNewsFragementNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private AllViewModel viewModel;
    AllAdapter allAdapter;


    private FragmentAllBinding binding;
    private ArrayList<RecordsItem> itemList;
    private ShimmerFrameLayout mShimmerViewContainer;
    EndlessRecyclerViewScrollListener recyclerViewScrollListener;


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
        mShimmerViewContainer = binding.shimmerViewContainer;

        itemList = new ArrayList<>();
        allAdapter = new AllAdapter(itemList);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(allAdapter);

        recyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };

        binding.recyclerView.addOnScrollListener(recyclerViewScrollListener);
    }

    private void loadNextDataFromApi(int page) {
        viewModel.getNextAll("0",page+"");
    }

    @Override
    public void nextResponse(AllModel allModel) {
        if (allModel.isStatus()){

            allAdapter.updateItems(allModel.getRecords());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void startAnimation() {
    }

    @Override
    public void onDestroyView() {
        // 1. First, clear the array of data
        itemList.clear();
// 2. Notify the adapter of the update
        allAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
// 3. Reset endless scroll listener when performing a new search
        recyclerViewScrollListener.resetState();
        super.onDestroyView();
    }

    @Override
    public void stopAnimation() {
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    @Override
    public void handleError(Throwable throwable) {
        Log.e("TAG","handle Error", throwable);
    }

    @Override
    public void response(AllModel allModel) {
        if (allModel.isStatus()){
          allAdapter.addItem(allModel.getRecords());
        }
    }
}
