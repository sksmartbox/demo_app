package com.sampra.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sampra.BR;
import com.sampra.R;
import com.sampra.data.model.ContactFormModel;
import com.sampra.data.model.notification.DeviceTokenModel;
import com.sampra.data.remote.ApiClient;
import com.sampra.data.remote.ApiInterface;
import com.sampra.databinding.ActivityMainBinding;
import com.sampra.ui.about.AboutUsActivity;
import com.sampra.ui.base.BaseActivity;
import com.sampra.ui.home.sharePref.SharedPref;
import com.sampra.ui.settings.SettingActivity;
import com.sampra.utils.ViewModelProviderFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity<ActivityMainBinding, HomeViewModel> implements View.OnClickListener, HomeNavigator {
    BottomNavigationView navView;
    ImageView about_us,settings;

    @Inject
    ViewModelProviderFactory factory;
    HomeViewModel mHomeViewModel;
    private ActivityMainBinding activityHomeBinding;
    private String token;
    private ApiInterface apiService;
    private AppBarConfiguration appBarConfiguration;

    public static Intent openActivity(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return com.sampra.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public HomeViewModel getViewModel() {
        mHomeViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        return mHomeViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        hitSendDeviceToken();
                        SharedPref.init(getApplicationContext());
                        SharedPref.write("token",token);

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        activityHomeBinding = getViewDataBinding();
        mHomeViewModel.setNavigator(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_news, R.id.navigation_live_chat, R.id.navigation_contact)
                .build();

//        if ()
//        appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_news, R.id.navigation_chatScreen, R.id.navigation_contact)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void hitSendDeviceToken() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        RequestBody device_token = RequestBody.create(MediaType.parse("text/plain"), token);
        Call<DeviceTokenModel> call = apiService.getDeviceToken(device_token);
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

    @Override
    protected void requestWindowFeature() {

    }

    private void initView() {
        navView = findViewById(R.id.nav_view);
        about_us = findViewById(R.id.about_us);
        settings = findViewById(R.id.setting);

        about_us.setOnClickListener(this);
        settings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.about_us:
                Intent intent_about = new Intent(this, AboutUsActivity.class);
                this.startActivity(intent_about);
                break;

            case R.id.setting:
                Intent intent_setting = new Intent(this, SettingActivity.class);
                this.startActivity(intent_setting);
                break;
        }
    }

    @Override
    public void handleError(Throwable throwable) {

    }
}
