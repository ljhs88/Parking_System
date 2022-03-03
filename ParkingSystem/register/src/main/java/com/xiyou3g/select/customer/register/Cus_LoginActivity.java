package com.xiyou3g.select.customer.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiyou3g.select.customer.register.Api.IdentifyingCodeService;
import com.xiyou3g.select.customer.register.Api.LoginService;
import com.xiyou3g.select.customer.register.bean.IdentifyingCodeResponse;
import com.xiyou3g.select.customer.register.bean.LoginData;
import com.xiyou3g.select.customer.register.bean.LoginResponse;
import com.xiyou3g.select.customer.register.util.NumberMatch;
import com.xiyou3g.select.customer.register.util.RetrofitManager;
import com.xiyou3g.select.customer.register.util.TimeCountUtil;
import com.xiyou3g.select.customer.register.util.ToastUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NonConstantResourceId")
@Route(path = "/customer/Cus_LoginActivity")
public class Cus_LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText accountEditText;
    private EditText passwordEditText;
    private boolean success;
    private RetrofitManager retrofitManager;
    private boolean loginSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_login);
        viewClick();
        hint_change();     //隐藏actionbar,将状态栏改为透明
        getCodeButton();
    }

    public void hint_change () {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }


    @Override
    public void onClick(View view) {
        String mobile;
        String smsCode;
        int id = view.getId();
        if (id == R.id.login_button) {
            if (isCheckBox()) {
                if (accountMatch(accountEditText.getText().toString())) {
                    mobile = accountEditText.getText().toString();
                    smsCode = passwordEditText.getText().toString();
                    if (passwordMatch(mobile, smsCode)) {
                        ToastUtil.getToast(Cus_LoginActivity.this, "登录成功");
                    }
                } else {
                    ToastUtil.getToast(this, "请填写正确的手机号");
                }
            } else {
                ToastUtil.getToast(this, "请勾选协议");
            }

        } /*else if (id == R.id.new_register) {
            start(R.id.new_register);
        } else if (id == R.id.phone_number_login) {
            start(R.id.phone_number_login);
        } else if (id == R.id.forget_password) {
            start(R.id.forget_password);
        }*/
    }


    private boolean passwordMatch(String mobile, String smsCode) {


        LoginService loginService = retrofitManager.getRetrofit().create(LoginService.class);
        LoginData loginData = new LoginData(mobile, smsCode);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), loginData.toString());

        loginService.login(requestBody).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                assert loginResponse != null;
                loginSuccess = loginResponse.getSuccess();
                getSharedPreferences("data", MODE_PRIVATE)
                        .edit().putString("userId", loginResponse.getData().getId()).apply();
                getSharedPreferences("data", MODE_PRIVATE)
                        .edit().putString("userToken", loginResponse.getData().getUserToken()).apply();
                getSharedPreferences("data", MODE_PRIVATE)
                        .edit().putString("mobile", loginResponse.getData().getMobile()).apply();

                //Log.d("TAG", "onResponse: " + loginResponse);
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                loginSuccess = false;
                //Log.e("TAG", "LoginOnFailure: ");
                runOnUiThread(() -> {
                    ToastUtil.getToast(Cus_LoginActivity.this, "连接服务器失败");
                });

            }
        });

        return loginSuccess;

    }

    private void getCodeButton() {
        Button get_code_button = findViewById(R.id.get_code_button);
        get_code_button.setOnClickListener(view -> {
            String mobile = accountEditText.getText().toString();
            if (accountMatch(mobile)) {
                TimeCountUtil timeCountUtil = new TimeCountUtil(60000L, 1000L, Cus_LoginActivity.this, get_code_button);
                retrofitManager = RetrofitManager.createRetrofitManager("http://101.201.78.192:8888/");
                IdentifyingCodeService identifyingCodeService = retrofitManager.getRetrofit().create(IdentifyingCodeService.class);

                identifyingCodeService.getIdentifyingCode(mobile).enqueue(new Callback<IdentifyingCodeResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<IdentifyingCodeResponse> call, @NonNull Response<IdentifyingCodeResponse> response) {
                        timeCountUtil.start();
                        IdentifyingCodeResponse identifyingCodeResponse = response.body();
                        assert identifyingCodeResponse != null;
                        success = identifyingCodeResponse.getSuccess();

                        //Log.d("TAG", "onResponse: " + identifyingCodeResponse);
                        if (!success) {
                            String msg = identifyingCodeResponse.getMsg();
                            runOnUiThread(()->{
                                Toast.makeText(Cus_LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<IdentifyingCodeResponse> call, @NonNull Throwable t) {
                        //Log.e("TAG", "smsOnFailure: " + t);
                        runOnUiThread(() -> ToastUtil.getToast(Cus_LoginActivity.this, "连接服务器失败"));
                    }
                });
            } else {
                ToastUtil.getToast(Cus_LoginActivity.this, "请填写正确的手机号");
            }
        });

    }

    private boolean isCheckBox() {
        CheckBox checkBox = findViewById(R.id.checkbox_login);
        return checkBox.isChecked();
    }

    public void start(int id) {
        Intent intent = new Intent(this, Cus_RegisterActivity.class);
        TextView textView = findViewById(id);
        String string = textView.getText().toString();
        intent.putExtra("text", string);
        startActivity(intent);
    }

    public boolean accountMatch (String phone_number) {
        return NumberMatch.number(phone_number);
    }

    private void viewClick() {

        Button login_button = findViewById(R.id.login_button);
        TextView new_register = findViewById(R.id.new_register);
        TextView phone_number_login = findViewById(R.id.phone_number_login);
        TextView forget_password = findViewById(R.id.forget_password);
        accountEditText = findViewById(R.id.cus_account);
        passwordEditText = findViewById(R.id.cus_password);
        accountEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        passwordEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        login_button.setOnClickListener(this);
        new_register.setOnClickListener(this);
        phone_number_login.setOnClickListener(this);
        forget_password.setOnClickListener(this);

    }

}