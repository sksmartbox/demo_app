package com.sampra.ui.home.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.sampra.R;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        newsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });


        TabLayout tabLayout = root.findViewById(R.id.tabbar);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("All")) {
                    navController = Navigation.findNavController(getActivity(), R.id.nav_host_news_fragment);
                    navController.navigate(R.id.allFragment);
                } else if (tab.getText().equals("Facebook")) {
                    navController = Navigation.findNavController(getActivity(), R.id.nav_host_news_fragment);
                    navController.navigate(R.id.facebookFragment);
                } else if (tab.getText().equals("Twitter")) {
                    navController = Navigation.findNavController(getActivity(), R.id.nav_host_news_fragment);
                    navController.navigate(R.id.twitterFragment);
                } else if (tab.getText().equals("Instagram")) {
                    navController = Navigation.findNavController(getActivity(), R.id.nav_host_news_fragment);
                    navController.navigate(R.id.instsgramFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return root;
    }
}