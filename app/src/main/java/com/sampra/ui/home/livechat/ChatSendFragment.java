package com.sampra.ui.home.livechat;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sampra.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatSendFragment extends Fragment {


    public ChatSendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_chat, container, false);
    }

}
