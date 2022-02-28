package com.xiyou3G.parkingsystem;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;


@Route(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] p;
        p = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(MainActivity.this, p, 1);

        findViewById(R.id.text111).setOnClickListener(View -> ARouter.getInstance().build("/map/MapActivity").withDouble("longitude", 116.740368).withDouble("latitude", 40.109056).withString("destination", "帆帆停车场").navigation());
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

}