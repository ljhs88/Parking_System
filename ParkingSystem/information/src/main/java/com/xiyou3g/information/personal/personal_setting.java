package com.xiyou3g.information.personal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiyou3g.information.R;
import com.xiyou3g.information.Utility.ActivityCollector;

public class personal_setting extends Fragment implements View.OnClickListener {

    private View view;
    private TextView money;
    private Button back;
    private Button backToDesk_button;
    private Button changeAccount;

    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_setting, container, false);

        setViewId();

        return view;
    }

    private void setViewId() {
        money = view.findViewById(R.id.money);
        back = view.findViewById(R.id.back);
        backToDesk_button = view.findViewById(R.id.backToDesk);
        changeAccount = view.findViewById(R.id.changeAccount);
        back.setOnClickListener(this);
        backToDesk_button.setOnClickListener(this);
        changeAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            getActivity().onBackPressed();
        } else if (id == R.id.changeAccount) {
            alertDialog();
        } else if (id == R.id.backToDesk) {
            backDialog();
        }
    }

    private void backDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("退出账号提示!");
        dialog.setMessage("确认退出当前账号吗?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                editor.putString("userId", "");
                editor.putString("mobile", "");
                editor.putString("userToken", "");
                editor.apply();
                ActivityCollector.finishAll();
            }
        });
        dialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("切换账号提示!");
        dialog.setMessage("确认切换账号吗?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
                ARouter.getInstance().build("/customer/Cus_LoginActivity").navigation();
            }
        });
        dialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
