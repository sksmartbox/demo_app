package com.sampra.ui.settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sampra.R;
import com.sampra.data.model.notification.DeviceTokenModel;
import com.sampra.data.remote.ApiClient;
import com.sampra.data.remote.ApiInterface;
import com.sampra.ui.home.sharePref.SharedPref;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    TextView edit_profile,user_phone,user_name;
    ImageView toggle_on,toggle_off;
    private int status;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPref.init(getApplicationContext());


    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        user_name = findViewById(R.id.user_name);
        user_phone = findViewById(R.id.user_phone);
        edit_profile = findViewById(R.id.edit_profile);
        toggle_on = findViewById(R.id.toggle_on);
        toggle_off = findViewById(R.id.toggle_off);
        getNotificationView();
        toggle_off.setOnClickListener(this);
        toggle_on.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        setSupportActionBar(toolbar);
        user_name.setText(SharedPref.read("user_name",null));
        user_phone.setText((SharedPref.read("user_phone",null)));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getNotificationView() {
        if(SharedPref.read("status",2) == 2 || SharedPref.read("status",2) == 0)
        {
            toggle_on.setVisibility(View.VISIBLE);
            toggle_off.setVisibility(View.GONE);
        }
        else
        {
            toggle_on.setVisibility(View.GONE);
            toggle_off.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.edit_profile:
//                showProfile();
                Intent intent = new Intent(this,EditProfile.class);
                startActivity(intent);
                break;
            case R.id.toggle_on:
                status = 1;
                SharedPref.write("status",status);
                hitUpdateNotificationOn(status);
                toggle_on.setVisibility(View.GONE);
                toggle_off.setVisibility(View.VISIBLE);
                break;
            case R.id.toggle_off:
                status = 0;
                SharedPref.write("status",status);
                hitUpdateNotificationOn(status);
                toggle_on.setVisibility(View.VISIBLE);
                toggle_off.setVisibility(View.GONE);
                break;

        }
    }

    private void hitUpdateNotificationOn(int status) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        RequestBody device_token = RequestBody.create(MediaType.parse("text/plain"), SharedPref.read("token",null));
        RequestBody notification_status = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(status));
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), "55");
        Call<DeviceTokenModel> call = apiService.getUpdateDeviceToken(device_token,notification_status,user_id);
        call.enqueue(new Callback<DeviceTokenModel>() {
            @Override
            public void onResponse(Call<DeviceTokenModel> call, Response<DeviceTokenModel> response) {
                //showPopUP(response);
            }

            @Override
            public void onFailure(Call<DeviceTokenModel> call, Throwable t) {

            }
        });
    }

    private void showProfile() {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Please create your profile.")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
//                .setNegativeButton(android.R.string.no, null)
//                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
