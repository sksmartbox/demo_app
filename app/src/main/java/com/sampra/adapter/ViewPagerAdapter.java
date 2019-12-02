package com.sampra.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.sampra.R;
import com.skyhope.showmoretextview.ShowMoreTextView;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private TextView txtCount;
//    private Integer [] images = {R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background,R.drawable.ic_home_black_24dp};
    private String [] pagertexts = {"sdfopsdfopsdofosdjfosdfposdpofkosdfopskdfkpoksdfsdfjiosjdfiosjfoijsdisdjifojsidfjiosdjfiosdjiofjs" +
        "dfopsdfopsdofosdjfosdfposdpofkosdfopskdfkpoksdfsdfjiosjdfiosjfoijsdisdjifojsidfjiosdjfiosdjiofjsdfsdpofoweowrwier",

        "sdfopsdfopsdofosdjfosdfposdpofkosdfopskdfkpoksdfsdfjiosjdfiosjfoijsdisdjifojsidfji" +
                "osdjfiosdjiosdfopsdfopsdofosdjfosdfposdpofkosdfopskdfkpoksdfsdfjiosjdfiosjfoijsdisdjifojsidfjiosdjfiosdjiofj" +
                "sdfsdpofoweowrwieroweoprowerwerosdfopsdopfo" +
                "sdfopsdfopsdofosdjfosdfposdpofkosdfopskdfkpoksdfsdfjiosjdfiosjfoijsdisdjifojsidfjiosdjfiosdjio"};
    ShowMoreTextView pagerdottext;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return pagertexts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        pagerdottext = view.findViewById(R.id.pagerdottext);
        txtCount = view.findViewById(R.id.txtCount);
        pagerdottext.setShowingChar(300);
        pagerdottext.setShowingLine(3);
        pagerdottext.addShowMoreText("Read More");
        pagerdottext.addShowLessText("Read Less");
        pagerdottext.setShowMoreColor(Color.BLUE); // or other color
        pagerdottext.setShowLessTextColor(Color.BLUE);
        pagerdottext.setText(pagertexts[position]);

        txtCount.setText(String.valueOf(pagertexts.length));
//        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//        imageView.setImageResource(images[position]);


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
