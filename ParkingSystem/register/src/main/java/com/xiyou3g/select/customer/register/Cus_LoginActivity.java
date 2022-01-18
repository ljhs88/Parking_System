package com.xiyou3g.select.customer.register;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiyou3g.select.customer.register.util.NumberMatch;

import java.util.Objects;

@SuppressLint("NonConstantResourceId")
@Route(path = "/customer/Cus_LoginActivity")
public class Cus_LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_login);
        viewClick();
        hint_change();     //隐藏actionbar,将状态栏改为透明

    }

    private void hint_change() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                EditText accountEditText = findViewById(R.id.cus_account);
                EditText passwordEditText = findViewById(R.id.cus_password);
                accountMatch(accountEditText.getText().toString());
                break;
            case R.id.new_register:
                break;
            case R.id.phone_number_login:
                break;
            case R.id.forget_password:
                break;
        }
    }

    public void accountMatch (String phone_number) {
        if (!NumberMatch.number(phone_number)) {
            Toast.makeText(this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewClick() {

        Button login_button = findViewById(R.id.login_button);
        TextView new_register = findViewById(R.id.new_register);
        TextView phone_number_login = findViewById(R.id.phone_number_login);
        TextView forget_password = findViewById(R.id.forget_password);

        login_button.setOnClickListener(this);
        new_register.setOnClickListener(this);
        phone_number_login.setOnClickListener(this);
        forget_password.setOnClickListener(this);

    }

}