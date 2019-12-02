package com.sampra.ui.home.livechat;

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

import com.sampra.R;

public class LiveChatFragment extends Fragment {

    private LiveChatViewModel liveChatViewModel;
    private  TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        liveChatViewModel =
                ViewModelProviders.of(this).get(LiveChatViewModel.class);
        View view = inflater.inflate(R.layout.fragment_live_chat, container, false);
        initView(view);
        liveChatViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return view;
    }

    private void initView(View view) {
//        textView = view.findViewById(R.id.text_dashboard);
    }
}