package com.xiyou3G.parkingsystem;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xiyou3G.parkingsystem.fragment.HomeFragment;
import com.xiyou3G.parkingsystem.fragment.ListFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import com.xiyou3g.baseapplication.collect.ActivityCollector;


@Route(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments;
    private static final int PARKING = 0;
    private static final int MY = 2;
    private static final int LIST = 1;
    private static int thisFragment = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCollector.addActivity(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Log.d("TAG", "onCreate: ");
            actionBar.hide();
        }

        //设置状态栏透明

        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        String[] p;
        p = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(MainActivity.this, p, 1);

        initView();
    }

    private void initView() {
        Fragment home_fragment = new HomeFragment();
        Fragment list_fragment = new ListFragment();
        Fragment information_fragment = (Fragment) ARouter.getInstance().build("/information/informationFragment").navigation();
        fragments = new Fragment[]{home_fragment, list_fragment, information_fragment};
        FrameLayout mainFrame = findViewById(R.id.frame_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.shared_frame, home_fragment).show(home_fragment).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home_parking) {
                    if (thisFragment != PARKING) {
                        switchFragment(thisFragment, PARKING);
                        thisFragment = PARKING;
                        return true;
                    }
                } else if (item.getItemId() == R.id.shared_my) {
                    if (item.getItemId() != MY) {
                        switchFragment(thisFragment, MY);
                        thisFragment = MY;
                        return true;
                    }
                } else if (item.getItemId() == R.id.list_parking) {
                    if (item.getItemId() != LIST) {
                        switchFragment(thisFragment, LIST);
                        thisFragment = LIST;
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 切换fragment
     * @param thisFragment 当前fragment
     * @param index 切换的fragment
     */
    private void switchFragment(int thisFragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragments[thisFragment]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.shared_frame, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }


    //请求允许的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            int result = grantResults[0];
            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "请同意定位权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

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

    public static String sHA1(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}