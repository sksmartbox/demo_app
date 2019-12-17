package com.sampra.ui.home.livechat;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.google.gson.JsonObject;
import com.sampra.R;
import com.sampra.data.model.chatModel.ChatAppMsgDTO;
import com.sampra.ui.adapter.ChatAppMsgAdapter;
import com.sampra.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class ChatScreenFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mMessageRecycler;
    private EditText msgInputText;
    private ImageView msgSendButton;
    private ArrayList<ChatAppMsgDTO> msgDtoList;
    private ChatAppMsgAdapter chatAppMsgAdapter;
    private com.github.nkzawa.socketio.client.Socket mSocket;

    private Emitter.Listener getAdminDetails = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };
    private Emitter.Listener addMessageResponse = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };
    private Emitter.Listener onReceivedMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    {
        try {
            mSocket = IO.socket(Constants.Socket_Base_Url);
            } catch (URISyntaxException e) {}
    }

    public ChatScreenFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_screen, container, false);
        initSocket();
        mMessageRecycler = view.findViewById(R.id.chat_recycler_view);
        msgInputText = view.findViewById(R.id.chat_input_msg);
        msgSendButton = view.findViewById(R.id.chat_send_msg);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mMessageRecycler.setLayoutManager(linearLayoutManager);
        msgDtoList = new ArrayList<ChatAppMsgDTO>();
        ChatAppMsgDTO msgDto = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_RECEIVED, "hello");
        msgDtoList.add(msgDto);

        // Create the data adapter with above data list.
        chatAppMsgAdapter = new ChatAppMsgAdapter(msgDtoList);

        // Set data adapter to RecyclerView.
        mMessageRecycler.setAdapter(chatAppMsgAdapter);

        msgSendButton.setOnClickListener(this);

        return view;
    }

    private void initSocket() {
        mSocket.connect();
        JSONObject jsonUpdateSocket = new JSONObject();
        try {
            jsonUpdateSocket.put("userId","55");
            jsonUpdateSocket.put("userSocketId",mSocket.id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("updatesocketId", jsonUpdateSocket);

        JSONObject jsonSocketGetMessage = new JSONObject();
        try {
            jsonSocketGetMessage.put("fromUserId","55");
            jsonSocketGetMessage.put("toUserId","6");
            jsonSocketGetMessage.put("requestBy","app");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("getMessages", jsonSocketGetMessage);

        mSocket.on("getMessagesResponse", onReceivedMessage);
        mSocket.on("addMessageResponse", addMessageResponse);
        mSocket.on("adminDetails", getAdminDetails);
//        mSocket.on("chatListRes", checkAdminStatus);
//        mSocket.on("connectedUser", connectedSupportUser);
//        mSocket.on("ticketClose", ticketClose);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    private void sendMsg() {
        String msgContent = msgInputText.getText().toString();
        if(!TextUtils.isEmpty(msgContent))
        {
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("date", "12-12-2019");
                jsonBody.put("fileFormat", "png");
                jsonBody.put("filePath", "");
                jsonBody.put("fromUserId", "55");
                jsonBody.put("message", msgContent);
                jsonBody.put("time", "12:22");
                jsonBody.put("toSocketId", mSocket.id());
                jsonBody.put("toUserId", "6");
                jsonBody.put("type", "text");
                jsonBody.put("name", "Rishabh");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSocket.emit("chatList", "55");
            mSocket.emit("addMessage", jsonBody);

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
