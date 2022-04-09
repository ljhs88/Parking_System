package com.xiyou3g.information.personal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.xiyou3g.information.R;
import com.xiyou3g.information.Utility.ToastUtil;
import com.xiyou3g.information.Utility.toolBar;

import com.xiyou3g.information.bean.getWalletBean;
import com.xiyou3g.information.reChargeActivity;
import com.xiyou3g.information.retrofit.mRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.xiyou3g.information.bean.*;

public class personal_wallet extends Fragment implements View.OnClickListener {


    private View view;
    private TextView money;
    private Button back;
    private Button reCharge;
    private String walletId;
    private String userId;
    private String token;
    private String mobile;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_wallet, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = pref.getString("userId", "");
        token = pref.getString("userToken", "");
        mobile = pref.getString("mobile", "");

        //userId = "946762136657330176";

        setToolbar();

        setViewId();

        getWalletInformation();

        return view;
    }

    private void getWalletInformation() {
        Retrofit retrofit = mRetrofit.getInstance();
        mRetrofit.api.getWalletInf(userId).enqueue(new Callback<getWalletBean>() {
            @Override
            public void onResponse(Call<getWalletBean> call, Response<getWalletBean> response) {
                getWalletBean bean = response.body();
                if (bean != null&&bean.getSuccess()==true&&bean.getData()!=null) {
                    Log.d("123", bean.toString());
                    money.setText(String.valueOf(bean.getData().getBalance()));
                    walletId = bean.getData().getId();
                } else {
                    ToastUtil.getToast(getContext(), "获取钱包信息失败！请重新尝试！");
                }
            }

            @Override
            public void onFailure(Call<getWalletBean> call, Throwable t) {
                ToastUtil.getToast(getContext(), "获取钱包信息失败！请重新尝试！");
            }
        });
    }

    private void setViewId() {
        money = view.findViewById(R.id.money);
        back = view.findViewById(R.id.back);
        reCharge = view.findViewById(R.id.re_charge);
        back.setOnClickListener(this);
        reCharge.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    private void setToolbar() {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("我的钱包");
        toolbar.setTitleTextColor(R.color.black);
        toolbar.setTitleMarginStart(toolBar.getAndroidScreenProperty(getActivity()));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            getActivity().onBackPressed();
        } else if (id == R.id.re_charge) {
            Intent intent = new Intent(getActivity(), reChargeActivity.class);
            intent.putExtra("walletId", walletId);
            startActivity(intent);
        }
    }

}
