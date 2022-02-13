package com.xiyou3g.information;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/information/Cus_InformationActivity")
public class Cus_InformationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button personal_button;
    private ImageView image_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_information);

        //设置状态栏透明
        makeStatusBarTransparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        TextView s = new TextView(this);
        s.onSaveInstanceState();
        // 获取控件实例
        getId();
        // 设置点击事件
        setButtonListen();
    }

    private void setButtonListen() {
        personal_button.setOnClickListener(this);
    }

    public void getId() {
        personal_button = findViewById ( R.id.personal_button );
        image_head = findViewById(R.id.head);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.personal_button) {
            Intent intent = new Intent(this, personActivity.class);
            intent.putExtra("select fragment", "personal");
            startActivity(intent);
        }
    }

    /**
     * 状态栏透明
     */
    public static void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}