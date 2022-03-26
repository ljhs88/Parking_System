package com.xiyou3G.parkingsystem.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.xiyou3G.parkingsystem.R;
import com.xiyou3G.parkingsystem.ShowSharedActivity;
import com.xiyou3G.parkingsystem.view.MyBanner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_home_fragment, container, false);
        initView();
        return view;
    }

    private void initView() {
        ConstraintLayout constraintLayoutStall = view.findViewById(R.id.shared_stall);
        constraintLayoutStall.setOnClickListener(View -> {
            Intent intent = new Intent(getContext(), ShowSharedActivity.class);
            intent.putExtra("status", "Stall");
            startActivity(intent);
        });
        ConstraintLayout constraintLayoutCharge = view.findViewById(R.id.shared_charge);
        constraintLayoutCharge.setOnClickListener(View -> {
            Intent intent = new Intent(getContext(), ShowSharedActivity.class);
            intent.putExtra("status", "Charge");
            startActivity(intent);
        });
        MyBanner myBanner = view.findViewById(R.id.home_banner);
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.banner1);
        list.add(R.drawable.banner2);
        myBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        myBanner.setBannerAnimation(Transformer.Default);

        myBanner.setImages(list);
        myBanner.setIndicatorGravity(BannerConfig.CENTER);
        myBanner.isAutoPlay(true);
        myBanner.setDelayTime(3000);
        myBanner.start();
    }


}
