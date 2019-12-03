package com.sampra.utils.rx;

import android.graphics.Paint;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class TextViewUtils {

    public static void setTextViewStrike(@NonNull TextView textView){
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
