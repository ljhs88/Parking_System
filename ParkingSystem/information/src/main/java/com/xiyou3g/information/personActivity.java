package com.xiyou3g.information;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiyou3g.information.Utility.ActivityCollector;
import com.xiyou3g.information.personal.personal_history;
import com.xiyou3g.information.personal.personal_history2;
import com.xiyou3g.information.personal.personal_idCard;
import com.xiyou3g.information.personal.personal_information;
import com.xiyou3g.information.personal.personal_setting;
import com.xiyou3g.information.personal.personal_wallet;

@Route(path = "/information/personalActivity")
public class personActivity extends AppCompatActivity{

    private Fragment fragment;
    private ConstraintLayout constraintLayout;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_personal);

        // 添加activity
        ActivityCollector.addActivity(this);

        //设置状态栏透明
        makeStatusBarTransparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        selectFragment();
    }

    /**
     * 点击空白输入键盘自动关闭
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (personActivity.this.getCurrentFocus() != null) {
                if (personActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(personActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void selectFragment() {
        Intent intent = getIntent();
        String content = intent.getStringExtra( "select fragment" );
        switch (content) {
            case "personal":
                replaceFragment(new personal_information());
                break;
            case "history":
                replaceFragment(new personal_history());
                break;
            case "history2":
                replaceFragment(new personal_history2());
                break;
            case "IdCard":
                replaceFragment(new personal_idCard());
                break;
            case "wallet":
                replaceFragment(new personal_wallet());
                break;
            case "setting":
                replaceFragment(new personal_setting());
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();//获取FragmentManager
        FragmentTransaction transaction = manager.beginTransaction();//调用beginTransaction()开启一个事务
        transaction.replace(R.id.fragment, fragment);//使用replace()方法向容器中添加碎片
        //transaction.addToBackStack(null);//将碎片放入返回栈中
        transaction.commit();//提交事务
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


    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //这里写你要在用户按下返回键同时执行的动作
            moveTaskToBack(false);            //核心代码：屏蔽返回行为
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
}