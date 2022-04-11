package com.xiyou3g.information.identity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xiyou3g.information.R;
import com.xiyou3g.information.retrofit.mRetrofit;
import com.xiyou3g.information.bean.requestInfBaseBean;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import com.xiyou3g.information.Utility.ToastUtil;

public class infBaseFragment extends Fragment implements View.OnClickListener {

    private View view;

    private String userid;
    private String token;
    private String mobile;

    private TextView title;
    private EditText edit_name;
    private EditText edit_IdCard;
    private EditText edit_carNum;

    private Button back;
    private Button infUpload;

    private String type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_identity_infbase, container, false);

        Bundle bundle = getArguments();
        type = bundle.getString("type");

        /**
         * 获取userid
         */
        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userid = pref.getString("userId", "");
        token = pref.getString("userToken", "");
        mobile = pref.getString("mobile", "");

        getViewId();


        return view;
    }

    private void getViewId() {
        title = view.findViewById(R.id.title);
        if ("infChange".equals(type)) {
            title.setText("变更身份信息");
        }
        edit_name = view.findViewById(R.id.name);
        edit_IdCard = view.findViewById(R.id.id_card);
        edit_carNum = view.findViewById(R.id.car_num);
        back = view.findViewById(R.id.back);
        infUpload = view.findViewById(R.id.infUpload);
        back.setOnClickListener(this);
        infUpload.setOnClickListener(this);
    }

    public void setInfUpload() {
        if (!String.valueOf(edit_name.getText()).equals("") &&
                !String.valueOf(edit_IdCard.getText()).equals("") &&
                !String.valueOf(edit_carNum.getText()).equals("")) {
            if ("infBase".equals(type)) {
                requestInfBaseBean.data data = new requestInfBaseBean.data(userid, String.valueOf(edit_name.getText()),
                        String.valueOf(edit_IdCard.getText()), null, null, String.valueOf(edit_carNum.getText()),
                        null, null);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), data.toString());
                mRetrofit.setIdentity(getActivity(), userid ,body);
                ToastUtil.getToast(getContext(), "实名认证信息上传成功！");
                Intent intent = new Intent();
                intent.putExtra("type", "infBase");
                getActivity().setResult(1, intent);
                getActivity().onBackPressed();
            } else if ("infChange".equals(type)) {
                requestInfBaseBean.data data = new requestInfBaseBean.data(userid, String.valueOf(edit_name.getText()),
                        String.valueOf(edit_IdCard.getText()), null, null, String.valueOf(edit_carNum.getText()),
                        null, null);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), data.toString());
                mRetrofit.setUpDataIdentity(getActivity(), body);
                ToastUtil.getToast(getContext(), "实名信息修改成功！");
            }
        } else {
            ToastUtil.getToast(getContext(), "请继续完善信息！");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            getActivity().onBackPressed();
        } else if (id == R.id.infUpload) {
            setInfUpload();
        }
    }
}
