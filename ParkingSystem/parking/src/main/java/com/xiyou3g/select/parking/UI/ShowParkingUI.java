package com.xiyou3g.select.parking.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.xiyou3g.select.parking.R;
import com.xiyou3g.select.parking.bean.ShowInformation;

public class ShowParkingUI extends ShowUI {

    private final Activity activity;
    private final ShowInformation information;

    public ShowParkingUI(Activity activity, ShowInformation information) {
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
        

        super.showText();
    }

    @Override
    public void showToolBar() {
        Toolbar toolbar = activity.findViewById(R.id.show_toolbar);
        toolbar.setTitle(information.getName());
        toolbar.setNavigationOnClickListener(view -> activity.finish());
        super.showToolBar();
    }
}
