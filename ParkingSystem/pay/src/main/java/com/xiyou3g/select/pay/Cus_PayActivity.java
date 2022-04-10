package com.xiyou3g.select.pay;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xiyou3g.select.pay.adapter.FragmentAdapter;
import com.xiyou3g.select.pay.fragment.InComeFragment;
import com.xiyou3g.select.pay.fragment.PayFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(path = "/pay/Cus_PayActivity")
public class Cus_PayActivity extends AppCompatActivity {

    private static final String TAG = "TAGPay";
    private final String[] tabString = new String[]{"支出账单", "收入账单"};
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private List<String> tabList;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_pay);
        Log.d(TAG, "onCreate: ");
        initView();

    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        tabLayout = findViewById(R.id.pay_tab);
        viewPager = findViewById(R.id.pay_viewpager);

        tabList = new ArrayList<>();
        tabList.addAll(Arrays.asList(tabString));

        fragmentList = new ArrayList<>();
        InComeFragment inComeFragment = new InComeFragment();
        PayFragment payFragment = new PayFragment();
        fragmentList.add(payFragment);
        fragmentList.add(inComeFragment);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(this, fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager, true, (tab, position) -> tab.setText(tabList.get(position))).attach();
        Log.d(TAG, "initView: ");
    }
}