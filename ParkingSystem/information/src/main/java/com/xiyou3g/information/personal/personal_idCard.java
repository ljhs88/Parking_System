package com.xiyou3g.information.personal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xiyou3g.information.R;
import com.xiyou3g.information.bean.requestInfBaseBean;
import com.xiyou3g.information.identityActivity;
import com.xiyou3g.information.retrofit.mRetrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.xiyou3g.information.Utility.ToastUtil;

public class personal_idCard extends Fragment implements View.OnClickListener {

    private View view;

    private final int INF_BASE_VALUE = 1;
    private final int CARD_PHOTO_VALUE = 2;

    private Button back;
    private Button infBaseButton;
    private Button cardPhotoButton;
    private Button infChangeButton;
    private Button infCancelButton;
    private Button backgroundButton;

    private String userid;
    private String token;
    private String mobile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_idcard, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userid = pref.getString("userId", "");
        token = pref.getString("userToken", "");
        mobile = pref.getString("mobile", "");

        //userid = "946762136657330176";

        getViewId();

        setButtonClick();

        judgeIdentity();

        return view;
    }

    /**
     * 实名认证判断数据
     */
    private void judgeIdentity() {
        mRetrofit.api.IsOrNoIdentity(userid).enqueue(new Callback<requestInfBaseBean>() {
            @Override
            public void onResponse(Call<requestInfBaseBean> call, Response<requestInfBaseBean> response) {
                requestInfBaseBean bean = response.body();
                if (bean != null) {
                    if (bean.getSuccess().equals("true")) {
                        infBaseButton.setText("已完善");
                        cardPhotoButton.setText("已上传");
                        infBaseButton.setTextColor(Color.parseColor("#DBDBDB"));
                        cardPhotoButton.setTextColor(Color.parseColor("#DBDBDB"));
                        infBaseButton.setEnabled(false);
                        cardPhotoButton.setEnabled(false);
                    } else if (bean.getStatus().equals("532")) {
                        ToastUtil.getToast(getContext(), bean.getMsg());
                    } else if (bean.getStatus().equals("533")) {
                        ToastUtil.getToast(getContext(), bean.getMsg());
                        infBaseButton.setText("已完善");
                    }
                }
            }

            @Override
            public void onFailure(Call<requestInfBaseBean> call, Throwable t) {
                Log.d("123", t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(getContext(), identityActivity.class);
        if (id == R.id.inf_base) {
            intent.putExtra("select fragment", "infBase");
            startActivityForResult(intent, INF_BASE_VALUE);
        } else if (id == R.id.card_photo) {
            intent.putExtra("select fragment", "cardPhoto");
            startActivityForResult(intent, CARD_PHOTO_VALUE);
        } else if (id == R.id.inf_change) {
            intent.putExtra("select fragment", "infChange");
            startActivity(intent);
        } else if (id == R.id.inf_cancel) {
            alertDialog();
        } else if (id == R.id.back) {
            getActivity().onBackPressed();
        } else if (id == R.id.background) {
            ToastUtil.getToast(getContext(), "开发中!尽请期待!");
        }
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("取消实名提示!");
        dialog.setMessage("确认取消实名吗?");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelClick();
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

    public void cancelClick() {
        if (infBaseButton.getText().equals("完善信息 >") &&
                cardPhotoButton.getText().equals("上传证件 >")) {
            Toast.makeText(getActivity(), "未实名认证!", Toast.LENGTH_SHORT).show();
            mRetrofit.cancelIdentity(getActivity(), userid);
            infBaseButton.setText("完善信息 >");
            cardPhotoButton.setText("上传证件 >");
            infBaseButton.setTextColor(Color.parseColor("#33ccff"));
            cardPhotoButton.setTextColor(Color.parseColor("#33ccff"));
            infBaseButton.setEnabled(true);
            cardPhotoButton.setEnabled(true);
        } else {
            mRetrofit.cancelIdentity(getActivity(), userid);
            infBaseButton.setText("完善信息 >");
            cardPhotoButton.setText("上传证件 >");
            infBaseButton.setTextColor(Color.parseColor("#33ccff"));
            cardPhotoButton.setTextColor(Color.parseColor("#33ccff"));
            infBaseButton.setEnabled(true);
            cardPhotoButton.setEnabled(true);
            ToastUtil.getToast(getContext(), "实名认证取消成功!");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String ok = null;
        Log.d("123", String.valueOf(resultCode));
        if (resultCode == INF_BASE_VALUE) {
            ok = data.getStringExtra("type");
            Log.d("123", "ok:"+ok);
            if (ok.equals("infBase")) {
                infBaseButton.setText("已完善");
                infBaseButton.setEnabled(false);
            }
        } else if (resultCode == CARD_PHOTO_VALUE) {
            ok = data.getStringExtra("type");
            if (ok.equals("cardPhoto")) {
                cardPhotoButton.setText("已上传");
                cardPhotoButton.setEnabled(false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setButtonClick() {
        back.setOnClickListener(this);
        infBaseButton.setOnClickListener(this);
        cardPhotoButton.setOnClickListener(this);
        infChangeButton.setOnClickListener(this);
        infCancelButton.setOnClickListener(this);
        backgroundButton.setOnClickListener(this);
    }

    private void getViewId() {
        back = view.findViewById(R.id.back);
        infBaseButton = view.findViewById(R.id.inf_base);
        cardPhotoButton = view.findViewById(R.id.card_photo);
        infChangeButton = view.findViewById(R.id.inf_change);
        infCancelButton = view.findViewById(R.id.inf_cancel);
        backgroundButton = view.findViewById(R.id.background);
    }
}
