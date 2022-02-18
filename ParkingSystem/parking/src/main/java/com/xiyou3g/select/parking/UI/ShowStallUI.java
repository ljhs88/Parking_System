package com.xiyou3g.select.parking.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiyou3g.select.parking.R;
import com.xiyou3g.select.parking.bean.ShowInformation;

public class ShowStallUI extends ShowUI {

    private final Activity activity;
    private final ShowInformation information;

    public ShowStallUI(Activity activity, ShowInformation information) {
        this.activity = activity;
        this.information = information;
    }

    @Override
    public void showImage() {
        Glide.with(activity).load(information.getBitmap()).into((ImageView) activity.findViewById(R.id.show_photo));
        super.showImage();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showText() {
        TextView showText1 = activity.findViewById(R.id.show_text1);
        TextView showText2 = activity.findViewById(R.id.show_text2);
        TextView showText3 = activity.findViewById(R.id.show_text3);
        showText1.setText(information.getName());
        showText2.setText("停车位一个小时" + information.getPrice() + "元");
        showText3.setText("  简介:" + information.getBriefIntroduction());

        super.showText();
    }

}

