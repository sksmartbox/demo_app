package com.sampra.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sampra.R;
import com.sampra.ui.intro.IntroActivity;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {


    private static final long DELAY_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spalsh);

        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = IntroActivity.openActivity(SplashActivity.this);
                        startActivity(intent);
//                        finish();
                    }
                }, DELAY_TIME);


    }
}
