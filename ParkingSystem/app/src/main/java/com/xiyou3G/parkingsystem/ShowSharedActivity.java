package com.xiyou3G.parkingsystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiyou3g.baseapplication.collect.ActivityCollector;

public class ShowSharedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shared);

        ActivityCollector.addActivity(this);

        Intent intent = getIntent();
        String status = intent.getStringExtra("Status");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = (Fragment) ARouter.getInstance().build("/parking/ParkingFragment").navigation();
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        fragment.setArguments(bundle);
        transaction.replace(R.id.shared_frame_show, fragment).show(fragment).commit();

    }
}