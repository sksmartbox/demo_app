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

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    TextView edit_profile;
    ImageView toggle_on,toggle_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        edit_profile = findViewById(R.id.edit_profile);
        toggle_on = findViewById(R.id.toggle_on);
        toggle_off = findViewById(R.id.toggle_off);
        toggle_off.setOnClickListener(this);
        toggle_on.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                toggle_on.setVisibility(View.GONE);
                toggle_off.setVisibility(View.VISIBLE);
                break;
            case R.id.toggle_off:
                toggle_on.setVisibility(View.VISIBLE);
                toggle_off.setVisibility(View.GONE);
                break;

        }
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
