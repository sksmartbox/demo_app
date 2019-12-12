package com.sampra.ui.home.livechat;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sampra.R;
import com.sampra.data.model.chatModel.ChatAppMsgDTO;
import com.sampra.ui.adapter.ChatAppMsgAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatScreenFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mMessageRecycler;
    private EditText msgInputText;
    private Button msgSendButton;
    private ArrayList<ChatAppMsgDTO> msgDtoList;
    private ChatAppMsgAdapter chatAppMsgAdapter;

    public ChatScreenFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_screen, container, false);

        mMessageRecycler = view.findViewById(R.id.chat_recycler_view);
        msgInputText = view.findViewById(R.id.chat_input_msg);
        msgSendButton = view.findViewById(R.id.chat_send_msg);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mMessageRecycler.setLayoutManager(linearLayoutManager);
        final List<ChatAppMsgDTO> msgDtoList = new ArrayList<ChatAppMsgDTO>();
        ChatAppMsgDTO msgDto = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_RECEIVED, "hello");
        msgDtoList.add(msgDto);

        // Create the data adapter with above data list.
        chatAppMsgAdapter = new ChatAppMsgAdapter(msgDtoList);

        // Set data adapter to RecyclerView.
        mMessageRecycler.setAdapter(chatAppMsgAdapter);

        msgSendButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.chat_send_msg:
                sendMsg();
                break;
        }
    }

    private void sendMsg() {
        String msgContent = msgInputText.getText().toString();
        if(!TextUtils.isEmpty(msgContent))
        {
            ChatAppMsgDTO msgDto = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_SENT, msgContent);
            msgDtoList.add(msgDto);

            int newMsgPosition = msgDtoList.size() - 1;

            // Notify recycler view insert one new data.
            chatAppMsgAdapter.notifyItemInserted(newMsgPosition);

            // Scroll RecyclerView to the last message.
            mMessageRecycler.scrollToPosition(newMsgPosition);

            // Empty the input edit text box.
            msgInputText.setText("");
        }
    }
}
