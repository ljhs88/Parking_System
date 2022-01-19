package com.xiyou3g.select.customer.register;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiyou3g.select.customer.register.util.CommonTool;
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

    public void hint_change () {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                EditText accountEditText = findViewById(R.id.cus_account);
                EditText passwordEditText = findViewById(R.id.cus_password);
                accountMatch(accountEditText.getText().toString());
                accountEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                break;
            case R.id.new_register:
                start(R.id.new_register);
                break;
            case R.id.phone_number_login:
                start(R.id.phone_number_login);
                break;
            case R.id.forget_password:
                start(R.id.forget_password);
                break;
        }
    }

    public void start(int id) {
        Intent intent = new Intent(this, Cus_RegisterActivity.class);
        TextView textView = findViewById(id);
        String string = textView.getText().toString();
        intent.putExtra("text", string);
        startActivity(intent);
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