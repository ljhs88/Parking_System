package com.xiyou3g.select.registers.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiyou3g.select.customer.register.R;

@Route(path = "/administrator/Adm_LoginActivity")
public class Adm_LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_login);
    }
}