package com.xiyou3g.select.pay.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter {

    List<Fragment> list;

    public FragmentAdapter(FragmentActivity fa, List<Fragment> list) {
        super(fa);
        this.list = list;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount: " + list.size());
        return list.size();
    }
}
