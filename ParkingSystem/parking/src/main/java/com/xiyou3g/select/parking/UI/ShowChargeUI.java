package com.xiyou3g.select.parking.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.xiyou3g.select.parking.R;
import com.xiyou3g.select.parking.bean.ShowInformation;
import com.xiyou3g.select.parking.bean.chargebean;
import com.xiyou3g.select.parking.util.RetrofitManager;

public class ShowChargeUI extends ShowUI {

    private RetrofitManager retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");
    private final Activity activity;
    private final chargebean information;

    public ShowChargeUI(Activity activity, chargebean information) {
        this.activity = activity;
        this.information = information;
    }

    @Override
    public void showImage() {
        Glide.with(activity).load(information.getData().get(0).getOwnerImage()).
                into((ImageView) activity.findViewById(R.id.show_photo));
        super.showImage();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void showText() {
        TextView adminName = activity.findViewById(R.id.show_text2);
        TextView adminMobile = activity.findViewById(R.id.show_text3);
        TextView ownerName = activity.findViewById(R.id.show_text5);
        TextView ownerMobile = activity.findViewById(R.id.show_text6);
        TextView ownerNum = activity.findViewById(R.id.show_text8);
        TextView hourPrice = activity.findViewById(R.id.show_text9);
        TextView province = activity.findViewById(R.id.show_text10);
        TextView location = activity.findViewById(R.id.show_text11);
        TextView overTimePrice = activity.findViewById(R.id.show_text12);

        adminName.setText(information.getData().get(0).getAdminName());
        adminMobile.setText(information.getData().get(0).getAdminMobile());
        ownerName.setText(information.getData().get(0).getOwnerName());
        ownerMobile.setText(information.getData().get(0).getOwnerMobile());
        ownerNum.setText(information.getData().get(0).getOwnerNum());
        hourPrice.setText(String.valueOf(information.getData().get(0).getPrice()));
        province.setText(information.getData().get(0).getProvince());
        location.setText(information.getData().get(0).getAddress());
        overTimePrice.setText(information.getData().get(0).getFinePrice());

        super.showText();
    }

    @Override
    public void showToolBar() {
        Toolbar toolbar = activity.findViewById(R.id.show_toolbar);
        toolbar.setTitle(information.getData().get(0).getOwnerNum());
        toolbar.setNavigationOnClickListener(view -> activity.finish());
        super.showToolBar();
    }
}
