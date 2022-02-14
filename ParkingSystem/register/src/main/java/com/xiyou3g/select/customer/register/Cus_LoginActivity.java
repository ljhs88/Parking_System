package com.xiyou3g.select.customer.register;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiyou3g.select.customer.register.util.NumberMatch;
import com.xiyou3g.select.customer.register.util.TimeCountUtil;

@SuppressLint("NonConstantResourceId")
@Route(path = "/customer/Cus_LoginActivity")
public class Cus_LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText accountEditText;
    private EditText passwordEditText;
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
        int id = view.getId();
        if (id == R.id.login_button) {
            if (isCheckBox()) {
                if (accountMatch(accountEditText.getText().toString())) {
                    if (passwordMatch()) {

                    }
                } else {
                    Toast.makeText(this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请勾选协议", Toast.LENGTH_SHORT).show();
            }

        } /*else if (id == R.id.new_register) {
            start(R.id.new_register);
        } else if (id == R.id.phone_number_login) {
            start(R.id.phone_number_login);
        } else if (id == R.id.forget_password) {
            start(R.id.forget_password);
        }*/
    }

    private boolean passwordMatch() {
        return true;
    }

    private void getCodeButton() {
        Button get_code_button = findViewById(R.id.get_code_button);
        get_code_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accountMatch(accountEditText.getText().toString())) {
                    TimeCountUtil timeCountUtil = new TimeCountUtil(60000L, 1000L, Cus_LoginActivity.this, get_code_button);
                    timeCountUtil.start();
                } else {
                    Toast.makeText(Cus_LoginActivity.this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                }
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