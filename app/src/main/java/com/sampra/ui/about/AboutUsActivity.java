package com.sampra.ui.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sampra.R;
import com.sampra.adapter.ViewPagerAdapter;
import com.sampra.data.model.about_us.AboutUsModel;
import com.sampra.data.model.about_us.Datum;
import com.sampra.data.model.about_us.Result;
import com.sampra.data.remote.ApiClient;
import com.sampra.data.remote.ApiInterface;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutUsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ShowMoreTextView textView,textView1;
    TextView about;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private ApiInterface apiService;
    private List<Datum> data;
    List<Result> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<AboutUsModel> call = apiService.getAboutUsDetails();
        call.enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(Call<AboutUsModel> call, Response<AboutUsModel> response) {
                int statusCode = response.code();
                data = response.body().getData();
                results = response.body().getResult();
                about.setText(Html.fromHtml(data.get(0).getAboutUs()));
                textView.setText(Html.fromHtml(data.get(1).getAboutUs()));
                getReadMore(textView);
                textView1.setText(Html.fromHtml(data.get(2).getAboutUs()));
                getReadMore(textView1);
                setAdapter();
            }

            @Override
            public void onFailure(Call<AboutUsModel> call, Throwable t) {

            }
        });

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i] = new ImageView(getApplicationContext());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setAdapter() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(results,this);
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
    }

    private void getReadMore(ShowMoreTextView textView) {
        textView.setShowingChar(100);
        textView.setShowingLine(2);
        textView.addShowMoreText("Read More");
        textView.addShowLessText("Read Less");
        textView.setShowMoreColor(Color.BLUE); // or other color
        textView.setShowLessTextColor(Color.BLUE);
    }


    private void initView() {
        about = findViewById(R.id.about);
        toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.text_view_show_more);
        textView1 = findViewById(R.id.text_view_show_more1);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
