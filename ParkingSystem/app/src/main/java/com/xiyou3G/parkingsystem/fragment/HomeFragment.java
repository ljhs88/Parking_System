package com.xiyou3G.parkingsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.xiyou3G.parkingsystem.R;
import com.xiyou3G.parkingsystem.ShowSharedActivity;

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
    }


}
