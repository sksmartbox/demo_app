package com.sampra.ui.home.news.twitter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.sampra.BR;
import com.sampra.R;
import com.sampra.data.model.AllModel;
import com.sampra.data.model.RecordsItem;
import com.sampra.databinding.FragmentInstsgramBinding;
import com.sampra.databinding.FragmentTwitterBinding;
import com.sampra.ui.adapter.AllAdapter;
import com.sampra.ui.adapter.EndlessRecyclerViewScrollListener;
import com.sampra.ui.base.BaseFragment;
import com.sampra.ui.home.news.instagram.InstagramViewModel;
import com.sampra.utils.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;


public class TwitterFragment extends BaseFragment<FragmentTwitterBinding, TwitterViewModel> implements TwitterFragementNavigator {


    @Inject
    ViewModelProviderFactory factory;
    private TwitterViewModel viewModel;
    FragmentTwitterBinding binding;
    private ShimmerFrameLayout mShimmerViewContainer;
    private ArrayList<RecordsItem> itemList;
    private AllAdapter allAdapter;
    private EndlessRecyclerViewScrollListener recyclerViewScrollListener;


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
        viewModel.getNextAll("2",page+"");
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
