package com.xiyou3G.parkingsystem.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xiyou3G.parkingsystem.R;
import com.xiyou3g.select.pay.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFragment extends Fragment {

    private final String[] tabString = new String[]{"我的订单", "我的出租"};
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private List<String> tabList;
    private List<Fragment> fragmentList;
    private View view;
    private String TAG = "TAG";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_list_fragment, container, false);
        initView();
        return view;
    }

    private void initView() {
        Log.d(TAG, "initView: ");
        tabLayout = view.findViewById(R.id.list_tab);
        viewPager = view.findViewById(R.id.list_viewpager);

        tabList = new ArrayList<>();
        tabList.addAll(Arrays.asList(tabString));

        fragmentList = new ArrayList<>();
        OrderFragment orderFragment = new OrderFragment();
        RentFragment rentFragment = new RentFragment();
        fragmentList.add(orderFragment);
        fragmentList.add(rentFragment);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity(), fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager, true, (tab, position) -> tab.setText(tabList.get(position))).attach();
    }
}
