package com.xiyou3g.select.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.xiyou3g.select.parking.UI.ShowChargeUI;
import com.xiyou3g.select.parking.UI.ShowParkingUI;
import com.xiyou3g.select.parking.UI.ShowStallUI;
import com.xiyou3g.select.parking.UI.ShowUI;
import com.xiyou3g.select.parking.bean.ShowInformation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShowInformationActivity extends AppCompatActivity {

    private static int STATUS = 1;
    private static final int CHARGE = 1;
    private static final int PARKING = 2;
    private static final int STALL = 3;
    private ShowInformation showInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information);
        EventBus.getDefault().register(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //getStatus();
        //show();
    }

    private void show() {
        if (STATUS == CHARGE) {
            showInformation(new ShowChargeUI(this, showInformation));
        } else if (STATUS == PARKING) {
            showInformation(new ShowParkingUI(this, showInformation));
        } else if (STATUS == STALL) {
            showInformation(new ShowStallUI(this, showInformation));
        }
    }

    private void getStatus() {
        Intent intent = getIntent();
        STATUS = intent.getIntExtra("STATUS", 0);
    }

    private void showInformation (ShowUI showUI) {
        showUI.showImage();
        showUI.showText();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiveShowInformation(ShowInformation showInformation) {
        this.showInformation = showInformation;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}